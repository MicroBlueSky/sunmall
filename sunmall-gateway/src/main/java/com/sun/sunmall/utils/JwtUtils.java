package com.sun.sunmall.utils;

import com.sun.sunmall.common.api.ResultCode;
import com.sun.sunmall.common.exception.GateWayException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

@Slf4j
public class JwtUtils {

    /**
     * 认证服务器许可我们的网关的clientId(需要在oauth_client_details表中配置)
     */
    private static final String CLIENT_ID = "api-gateway";

    /**
     * 认证服务器许可我们的网关的client_secret(需要在oauth_client_details表中配置)
     */
    private static final String CLIENT_SECRET = "test";

    /**
     * 认证服务器暴露的获取token_key的地址
     */
    private static final String AUTH_TOKEN_KEY_URL = "http://sunmall-authcenter/oauth/token_key";

    /**
     * 请求头中的 token的开始
     */
    private static final String AUTH_HEADER = "bearer";

    /**
     * PUBLIC_KEY 中的开始字符
     */
    private static final String PUBLIC_KEY_BEGIN_CHAR = "-----BEGIN PUBLIC KEY-----\\n";

    /**
     * PUBLIC_KEY 中的结束字符
     */
    private static final String PUBLIC_KEY_END_CHAR = "\n-----END PUBLIC KEY-----";

    /*
     *
     */
    public static Claims validateJwtToken(String authHeader, PublicKey publicKey){
        String token = null;
        try {
            token = StringUtils.substringAfter(authHeader, AUTH_HEADER);
            Jws<Claims> claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            return claims.getBody();
        }catch (Exception e) {
            log.error("校验token异常:{},异常信息:{}",token,e.getMessage());
            throw new GateWayException(ResultCode.JWT_TOKEN_EXPIRE);
        }
    }

    /*
     *生产公钥key
     */
    public static PublicKey getPublicKey(RestTemplate restTemplate) throws GateWayException{
        String tokenKey = getTokenKeyByRemoteCall(restTemplate);
        try {
            String dealPublicKey = tokenKey.replaceAll(PUBLIC_KEY_BEGIN_CHAR, "").replaceAll(PUBLIC_KEY_END_CHAR, "");
//            java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            X509EncodedKeySpec publicKeyspec = new X509EncodedKeySpec(Base64.decodeBase64(dealPublicKey));
            KeyFactory rsa = KeyFactory.getInstance("RSA");
            PublicKey publicKey = rsa.generatePublic(publicKeyspec);
            log.info("生成公钥：{}", publicKey);
            return publicKey;
        }catch (Exception e) {
            log.info("生成公钥异常:{}",e.getMessage());
            throw new GateWayException(ResultCode.GEN_PUBLIC_KEY_ERROR);
        }
    }

    /*
     *  方法实现说明: 通过远程调用获取认证服务器颁发jwt的解析的key
     */
    private static String getTokenKeyByRemoteCall(RestTemplate restTemplate) {
        //封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID,CLIENT_SECRET);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);
        //远程调用获取tokenKey
        try {
            ResponseEntity<Map> exchange = restTemplate.exchange(AUTH_TOKEN_KEY_URL, HttpMethod.GET, entity, Map.class);
            String tokenKey = exchange.getBody().get("value").toString();
            log.info("去认证服务器获取Token_Key:{}",tokenKey);
            return tokenKey;
        }catch (Exception e){
            log.error("远程调用认证服务器获取Token_Key失败:{}",e.getMessage());
            throw new GateWayException(ResultCode.GET_TOKEN_KEY_ERROR);
        }
    }
}
