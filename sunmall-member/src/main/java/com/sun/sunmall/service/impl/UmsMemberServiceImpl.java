package com.sun.sunmall.service.impl;

import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.bean.UmsMemberExample;
import com.sun.sunmall.bean.UmsMemberLevel;
import com.sun.sunmall.bean.UmsMemberLevelExample;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.mapper.UmsMemberLevelMapper;
import com.sun.sunmall.mapper.UmsMemberMapper;
import com.sun.sunmall.service.RedisService;
import com.sun.sunmall.service.UmsMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    private static final Logger log = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired
    RedisService redisService;

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
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone,sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDS);
        String s = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        log.info("验证码：{}", s);
        return CommonResult.success(sb.toString(),"获取验证码成功");
    }

    /*
     * 对输入的验证码进行校验
     */
    private boolean verifyAuthCode(String authCode, String telephone) {
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        return authCode.equals(realAuthCode);
    }
}
