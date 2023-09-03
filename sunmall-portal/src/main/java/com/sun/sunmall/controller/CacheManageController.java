package com.sun.sunmall.controller;

import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.service.CacheManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 * @title CacheManageController
 * @description
 * @create 2023-09-03 16:37
 */
@RestController
@Api(tags = "CacheManageController", description = "缓存管理")
@RequestMapping("/cache")
public class CacheManageController {

    @Autowired
    private CacheManageService cacheManageService;

    @ApiOperation("强制本地缓存失效")
    @RequestMapping(value = "/invalid", method = RequestMethod.GET)
    public CommonResult invalidCache(@RequestParam(value = "cacheType") int cacheType) {
        cacheManageService.invalidCache(cacheType);
        return CommonResult.success("强制本地缓存失效完成");
    }

    @ApiOperation("刷新本地缓存")
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public CommonResult refreshCache(@RequestParam(value = "cacheType") int cacheType) {
        CommonResult result = cacheManageService.refreshCache(cacheType);
        return result;
    }
}
