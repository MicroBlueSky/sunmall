package com.sun.sunmall.service;

import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.promotion.domain.HomeContentResult;

/**
 * @author Administrator
 * @version 1.0
 * @title CacheManageService
 * @description
 * @create 2023-09-03 16:38
 */
public interface CacheManageService {

    /**
     * @description 是缓存失效
     */
    void invalidCache(int cacheType);

    /**
     * @description 刷新缓存
     */
    CommonResult refreshCache(int cacheType);

    /**
     * @description 预热缓存
     */
    void preHeatCache();

    /**
     * @description 从远程(Redis或者对应微服务)获取推荐内容
     */
    HomeContentResult getFromRemote();

    /**
     * @description 处理首页推荐品牌和商品内容
     */
    HomeContentResult recommendContent();
}
