package com.sun.sunmall.filter;

import com.sun.sunmall.common.api.ResultCode;
import com.sun.sunmall.common.exception.GateWayException;
import com.sun.sunmall.properties.NoAuthUrlProperties;
import com.sun.sunmall.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@EnableConfigurationProperties(value = {NoAuthUrlProperties.class})
public class AuthorizationFilter implements GlobalFilter, Ordered, InitializingBean {

    @Autowired
    NoAuthUrlProperties noAuthUrlProperties;

    @Autowired
    private RestTemplate restTemplate;

    private PublicKey publicKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.publicKey = JwtUtils.getPublicKey(restTemplate);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String currentUrl = exchange.getRequest().getURI().getPath();
        //1:不需要认证的url
        if(shouldSkip(currentUrl)) {
            log.info("跳过认证的URL:{}",currentUrl);
            return chain.filter(exchange);
        }
        //需要认证的url
        log.info("需要认证的url:{}",currentUrl);
        //解析出我们authorization的请求头 ，value bearer xxxxx
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        //判断Authorization的请求头是否为空
        if (StringUtils.isEmpty(authorization)) {
            log.info("需要认证的请求头：{}",authorization);
            throw new GateWayException(ResultCode.AUTHORIZATION_HEADER_IS_EMPTY);
        }
        //校验jwt,jwt不正确或超时抛出异常
        Claims claims = JwtUtils.validateJwtToken(authorization,publicKey);
        //把从jwt中解析出来的 用户登陆信息存储到请求头中
        ServerWebExchange webExchange = wrapHeader(exchange,claims);
        return chain.filter(webExchange);
    }

    /*
     * 把我们从jwt解析出来的用户信息存储到请求中
     */
    private ServerWebExchange wrapHeader(ServerWebExchange exchange, Claims claims) {
//        String loginUserInfo = JSON.toJSONString(claims);
        Map additionalInfo = claims.get("additionalInfo", Map.class);
        String memberId = additionalInfo.get("memberId").toString();
        String nickName = additionalInfo.get("nickName").toString();
        //headers中存放文件
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header("username", claims.get("user_name",String.class))
                .header("memberId", memberId)
                .header("nickName", nickName)
                .build();
        //将现在的request 变成 change对象
        return exchange.mutate().request(request).build();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 方法实现说明:不需要授权的路径
     * @author:smlz
     * @param currentUrl 当前请求路径
     * @return:
     * @exception:
     * @date:2019/12/26 13:49
     */
    private boolean shouldSkip(String currentUrl) {
        //路径匹配器(简介SpringMvc拦截器的匹配器)
        //比如/oauth/** 可以匹配/oauth/token    /oauth/check_token等
        PathMatcher pathMatcher = new AntPathMatcher();
        for(String skipPath:noAuthUrlProperties.getShouldSkipUrls()) {
            if(pathMatcher.match(skipPath,currentUrl)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPremisson(Claims claims,String currentUrl) {
        boolean hasPremisson = false;
        //登陆用户的权限集合判断
        List<String> premessionList = claims.get("authorities",List.class);
        for (String url: premessionList) {
            if(currentUrl.contains(url)) {
                hasPremisson = true;
                break;
            }
        }
        if(!hasPremisson){
            log.warn("权限不足");
            throw new GateWayException(ResultCode.FORBIDDEN);
        }

        return hasPremisson;
    }
}
