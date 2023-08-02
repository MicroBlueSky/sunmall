package com.sun.sunmall;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.sun.sunmall.config.RedisConfig;
import com.sun.sunmall.util.RedisOpsExtUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Administrator
 * @version 1.0
 * @title ProductionApplication
 * @description
 * @create 2023-07-30 17:06
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@Import(RedisConfig.class)
@EnableTransactionManagement
@MapperScan({"com.sun.sunmall.mapper","com.sun.sunmall.dao"})
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class,args);
    }
}
