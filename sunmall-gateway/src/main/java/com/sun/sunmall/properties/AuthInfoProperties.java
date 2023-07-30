package com.sun.sunmall.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("sun.auth.token")
public class AuthInfoProperties {

    private String authTokenKeyUrl;
    private String clientId;
    private String clientSecret;
}
