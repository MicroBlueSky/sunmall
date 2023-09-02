package com.sun.sunmall;

import com.sun.sunmall.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 * @version 1.0
 * @title ProductionApplication
 * @description
 * @create 2023-07-30 17:06
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
@Import(RedisConfig.class)
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class,args);
    }
}
