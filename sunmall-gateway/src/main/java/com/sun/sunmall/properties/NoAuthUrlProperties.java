package com.sun.sunmall.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("sun.gateway")
public class NoAuthUrlProperties {

    private List<String> shouldSkipUrls;
}
