package com.sun.sunmall;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 * @version 1.0
 * @title OrderCurrentApplication
 * @description
 * @create 2023-08-02 22:42
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan({"com.sun.sunmall.mapper", "com.sun.sunmall.dao"})
@EnableSwagger2
public class OrderCurrentApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderCurrentApplication.class);
    }
}
