package com.sun;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.sun.sunmall.mapper"})
public class AuthcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthcenterApplication.class);
    }
}
