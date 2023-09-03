package com.sun.sunmall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.common.config.PromotionRedisKey;
import com.sun.sunmall.feign.product.PromotionFeignClient;
import com.sun.sunmall.model.SmsHomeAdvertise;
import com.sun.sunmall.model.normal.CmsSubject;
import com.sun.sunmall.model.product.PmsBrand;
import com.sun.sunmall.model.product.PmsProduct;
import com.sun.sunmall.promotion.domain.FlashPromotionProduct;
import com.sun.sunmall.promotion.domain.HomeContentResult;
import com.sun.sunmall.service.CacheManageService;
import com.sun.sunmall.util.RedisDistrLock;
import com.sun.sunmall.util.RedisOpsExtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title CacheManageServiceImpl
 * @description
 * @create 2023-09-03 16:39
 */
@Service
@Slf4j
public class CacheManageServiceImpl implements CacheManageService {
    @Autowired
    private RedisOpsExtUtil redisOpsExtUtil;
    @Autowired
    private PromotionRedisKey promotionRedisKey;
    @Autowired
    private RedisDistrLock redisDistrLock;

    @Autowired
    @Qualifier("promotion")
    private Cache<String, HomeContentResult> promotionCache;

    @Autowired
    @Qualifier("promotionBak")
    private Cache<String, HomeContentResult> promotionCacheBak;

    @Autowired
    @Qualifier("secKill")
    private Cache<String, List<FlashPromotionProduct>> secKillCache;

    @Autowired
    @Qualifier("secKillBak")
    private Cache<String, List<FlashPromotionProduct>> secKillCacheBak;
    @Autowired
    private PromotionFeignClient promotionFeignClient;

    @Override
    public void invalidCache(int cacheType) {
        if(0 == cacheType) promotionCache.invalidateAll();
        if(1 == cacheType) promotionCacheBak.invalidateAll();
        if(2 == cacheType) {
            promotionCacheBak.invalidateAll();
            promotionCache.invalidateAll();
        }
    }

    @Override
    public CommonResult refreshCache(int cacheType) {
        final String brandKey = promotionRedisKey.getBrandKey();
        HomeContentResult result = getFromRemote();
        if(null != result){
            if(0 == cacheType) promotionCache.put(brandKey,result);
            if(1 == cacheType) promotionCacheBak.put(brandKey,result);
            if(2 == cacheType) {
                promotionCacheBak.put(brandKey,result);
                promotionCache.put(brandKey,result);
            }
            return CommonResult.success("刷新本地缓存完成");
        }else{
            return CommonResult.failed("从远程服务未获得数据，刷新本地缓存失败");
        }
    }


    @Override
    public void preHeatCache() {
        try {
            final String secKillKey = promotionRedisKey.getSecKillKey();
            List<FlashPromotionProduct> secKillResult = getSecKillFromRemote();
            secKillCache.put(secKillKey,secKillResult);
            secKillCacheBak.put(secKillKey,secKillResult);
            log.info("秒杀数据本地缓存预热完成");
        } catch (Exception e) {
            log.error("秒杀数据缓存预热失败：",e);
        }

        try {
            if(promotionRedisKey.isAllowLocalCache()){
                final String brandKey = promotionRedisKey.getBrandKey();
                HomeContentResult result = getFromRemote();
                promotionCache.put(brandKey,result);
                promotionCacheBak.put(brandKey,result);
                log.info("promotionCache 数据缓存预热完成");
            }
        } catch (Exception e) {
            log.error("promotionCache 数据缓存预热失败：",e);
        }
    }

    @Override
    public HomeContentResult getFromRemote() {
        List<PmsBrand> recommendBrandList = null;
        List<SmsHomeAdvertise> smsHomeAdvertises = null;
        List<PmsProduct> newProducts  = null;
        List<PmsProduct> recommendProducts  = null;
        HomeContentResult result = null;
        /*从redis获取*/
        if(promotionRedisKey.isAllowRemoteCache()){
            recommendBrandList = redisOpsExtUtil.getListAll(promotionRedisKey.getBrandKey(), PmsBrand.class);
            smsHomeAdvertises = redisOpsExtUtil.getListAll(promotionRedisKey.getHomeAdvertiseKey(), SmsHomeAdvertise.class);
            newProducts = redisOpsExtUtil.getListAll(promotionRedisKey.getNewProductKey(), PmsProduct.class);
            recommendProducts = redisOpsExtUtil.getListAll(promotionRedisKey.getRecProductKey(), PmsProduct.class);
        }
        /*redis没有则从微服务中获取*/
        if(CollectionUtil.isEmpty(recommendBrandList)
                ||CollectionUtil.isEmpty(smsHomeAdvertises)
                ||CollectionUtil.isEmpty(newProducts)
                ||CollectionUtil.isEmpty(recommendProducts)) {
            result = promotionFeignClient.content(0).getData();
            if(CollectionUtils.isEmpty(result.getBrandList())){
                redisDistrLock.lock(promotionRedisKey.getDlBrandKey(),promotionRedisKey.getDlTimeout());
                try {
                    redisOpsExtUtil.putListAllRight(promotionRedisKey.getBrandKey(),recommendBrandList);
                } finally {
                    redisDistrLock.unlock(promotionRedisKey.getDlBrandKey());
                }
                result.setBrandList(result.getBrandList());
                log.info("品牌推荐信息存入缓存，键{}" ,promotionRedisKey.getBrandKey());
            }
        }else{
            result = new HomeContentResult();
            result.setBrandList(recommendBrandList);
            result.setAdvertiseList(smsHomeAdvertises);
            result.setHotProductList(recommendProducts);
            result.setNewProductList(newProducts);
        }
        return result;
    }

    public List<FlashPromotionProduct> getSecKillFromRemote(){
        List<FlashPromotionProduct> result = redisOpsExtUtil.getListAll(promotionRedisKey.getSecKillKey(),
                FlashPromotionProduct.class);
        if(CollectionUtil.isEmpty(result)){
            result = promotionFeignClient.getHomeSecKillProductList().getData();
        }
        return result;
    }

    @Override
    public HomeContentResult recommendContent() {
        /*品牌和产品在本地缓存中统一处理，有则视为同有，无则视为同无*/
        final String brandKey = promotionRedisKey.getBrandKey();
        final boolean allowLocalCache = promotionRedisKey.isAllowLocalCache();
        /*先从本地缓存中获取推荐内容*/
        HomeContentResult result = allowLocalCache ?
                promotionCache.getIfPresent(brandKey) : null;
        if(result == null){
            result = allowLocalCache ?
                    promotionCacheBak.getIfPresent(brandKey) : null;
        }
        /*本地缓存中没有*/
        if(result == null){
            log.warn("从本地缓存中获取推荐品牌和商品失败，可能出错或禁用了本地缓存[allowLocalCache = {}]",allowLocalCache);
            result = getFromRemote();
            if(null != result) {
                promotionCache.put(brandKey,result);
                promotionCacheBak.put(brandKey,result);
            }
        }
        /* 处理秒杀内容*/
        final String secKillKey = promotionRedisKey.getSecKillKey();
        List<FlashPromotionProduct> secKills = secKillCache.getIfPresent(secKillKey);
        if(CollectionUtils.isEmpty(secKills)){
            secKills = secKillCacheBak.getIfPresent(secKillKey);
        }
        if(CollectionUtils.isEmpty(secKills)){
            /*极小的概率出现本地两个缓存同时失效的问题，
            从远程获取时，只从Redis缓存中获取，不从营销微服务中获取，
            避免秒杀的流量冲垮营销微服务*/
            secKills = getSecKillFromRemote();
            if(!CollectionUtils.isEmpty(secKills)) {
                secKillCache.put(secKillKey,secKills);
                secKillCacheBak.put(secKillKey,secKills);
            }else{
                /*Redis缓存中也没有秒杀活动信息，此处用一个空List代替，
                 * 其实可以用固定的图片或信息，作为降级和兜底方案*/
                secKills = new ArrayList<FlashPromotionProduct>();
            }
        }
        result.setHomeFlashPromotion(secKills);
        // fixme CMS本次不予实现，设置空集合
        result.setSubjectList(new ArrayList<CmsSubject>());
        //获取推荐专题
        //TODO homeDao.getRecommendSubjectList(0,4)
        result.setSubjectList(null);
        return result;
    }
}
