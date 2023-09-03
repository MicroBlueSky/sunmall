package com.sun.sunmall.task;

import com.sun.sunmall.service.CacheManageService;
import com.sun.sunmall.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @title PreHeatCacheTask
 * @description
 * @create 2023-09-03 17:01
 */
@Component
@Slf4j
public class PreHeatCacheTask implements CommandLineRunner {
    @Autowired
    private CacheManageService cacheManageService;

    @Override
    public void run(String... args) throws Exception {
        for(String str : args) {
            log.info("系统启动命令行参数: {}",str);
        }
        cacheManageService.preHeatCache();
    }
}
