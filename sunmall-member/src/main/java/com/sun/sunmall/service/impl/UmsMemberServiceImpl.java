package com.sun.sunmall.service.impl;

import com.ramostear.captcha.HappyCaptcha;
import com.sun.sunmall.MDA;
import com.sun.sunmall.bean.*;
import com.sun.sunmall.bean.domain.PortalMemberInfo;
import com.sun.sunmall.bean.dto.UserLoginDTO;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.common.api.TokenInfo;
import com.sun.sunmall.common.constant.RedisMemberPrefix;
import com.sun.sunmall.mapper.UmsMemberLevelMapper;
import com.sun.sunmall.mapper.UmsMemberMapper;
import com.sun.sunmall.service.MemberReceiveAddressService;
import com.sun.sunmall.service.UmsMemberCenterService;
import com.sun.sunmall.service.UmsMemberService;
import com.sun.sunmall.util.RedisOpsExtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    private static final Logger log = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired
    RedisOpsExtUtil redisOpsExtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsMemberMapper umsMemberMapper;
    @Autowired
    private UmsMemberLevelMapper umsMemberLevelMapper;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    UmsMemberCenterService umsMemberCenterService;
    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CommonResult register(String username, String password, String telephone, String authCode) {
        //验证验证码
        if (!verifyAuthCode(authCode,telephone)) {
            return CommonResult.failed("验证码错误");
        }
        //查询是否存在该用户
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        example.or(example.createCriteria().andPhoneEqualTo(telephone));
        List<UmsMember> list = umsMemberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return CommonResult.failed("该用户已经注册");
        }
        //没有该用户进行添加操作
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        umsMember.setNickname(username);
        umsMember.setPhone(telephone);
        umsMember.setPassword(passwordEncoder.encode(password));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        //获取默认会员等级并设置
        UmsMemberLevelExample levelExample = new UmsMemberLevelExample();
        levelExample.createCriteria().andDefaultStatusEqualTo(1);
        List<UmsMemberLevel> umsMemberLevels = umsMemberLevelMapper.selectByExample(levelExample);
        if (!CollectionUtils.isEmpty(umsMemberLevels)) {
            umsMember.setMemberLevelId(umsMemberLevels.get(0).getId());
        }
        umsMemberMapper.insert(umsMember);
        umsMember.setPassword(null);
        return CommonResult.success(null,"注册成功");
    }

    @Override
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0 ; i < 6; i++){
            sb.append(random.nextInt(10));
        }
        //验证码绑定手机号病存储到redis
        redisOpsExtUtil.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone,sb.toString());
        redisOpsExtUtil.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS,TimeUnit.SECONDS);
        String s = redisOpsExtUtil.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        log.info("验证码：{}", s);
        return CommonResult.success(sb.toString(),"获取验证码成功");
    }

    @Override
    public CommonResult login(UserLoginDTO dto, HttpServletRequest request) {
        if (!HappyCaptcha.verification(request, dto.getAuthCode(), true)) {
            return CommonResult.failed("请输入正确的验证码");
        }
        TokenInfo tokenInfo = logon(dto.getUsername(),dto.getPassword());
        if (tokenInfo == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",tokenInfo.getAccess_token());
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("refreshToken",tokenInfo.getRefresh_token());
        String memberId = tokenInfo.getAdditionalInfo().get("memberId");
        tokenMap.put("memberId", memberId);
        tokenMap.put("nickName", dto.getUsername());

        //用户登录成功后，将用户相关信息存入Redis，12小时后过期
        Long longMemberId = Long.valueOf(memberId);
        PortalMemberInfo memberInfo = umsMemberCenterService.getMemberInfo(longMemberId);
        redisOpsExtUtil.set(RedisMemberPrefix.MEMBER_INFO_PREFIX + memberId, memberInfo,60*60*12, TimeUnit.SECONDS);
        List<UmsMemberReceiveAddress> addressList = memberReceiveAddressService.list(longMemberId);
        redisOpsExtUtil.putListAllRight(RedisMemberPrefix.MEMBER_ADDRESS_PREFIX + memberId,addressList);
        redisOpsExtUtil.expire(RedisMemberPrefix.MEMBER_ADDRESS_PREFIX + memberId,60*60*12, TimeUnit.SECONDS);
        return CommonResult.success(tokenMap);
    }

    private TokenInfo logon(String username, String password) {
        ResponseEntity<TokenInfo> response;
        try {
            response = restTemplate.exchange(MDA.OAUTH_LOGIN_URL, HttpMethod.POST, wrapOauthTokenRequest(username, password), TokenInfo.class);
            TokenInfo tokenInfo = response.getBody();
            log.info("根据用户名:{}登陆成功:TokenInfo:{}",username,tokenInfo);
            return tokenInfo;
        }catch (Exception e){
            log.error("根据用户名:{}登陆异常:{}",username,e);
            return null;
        }
    }

    /*
     * 封装用户到认证中心的请求头 和请求参数
     */
    private HttpEntity<MultiValueMap<String, String>> wrapOauthTokenRequest(String userName, String password) {

        //封装oauth2 请求头 clientId clientSecret
        HttpHeaders httpHeaders = wrapHttpHeaders();

        //封装请求参数
        MultiValueMap<String, String> reqParams = new LinkedMultiValueMap<>();
        reqParams.add(MDA.USER_NAME,userName);
        reqParams.add(MDA.PASS,password);
        reqParams.add(MDA.GRANT_TYPE,MDA.PASS);
        reqParams.add(MDA.SCOPE,MDA.SCOPE_AUTH);
        //封装请求参数
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(reqParams, httpHeaders);
        return entity;
    }

    /**
     * 方法实现说明:封装请求头
     * @author:smlz
     * @return:HttpHeaders
     * @exception:
     * @date:2020/1/22 16:10
     */
    private HttpHeaders wrapHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(MDA.CLIENT_ID,MDA.CLIENT_SECRET);
        return httpHeaders;
    }

    /*
     * 对输入的验证码进行校验
     */
    private boolean verifyAuthCode(String authCode, String telephone) {
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisOpsExtUtil.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }
}
