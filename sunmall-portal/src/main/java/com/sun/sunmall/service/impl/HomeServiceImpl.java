package com.sun.sunmall.service.impl;

import com.sun.sunmall.bean.PmsProduct;
import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.domain.HomeContentResult;
import com.sun.sunmall.feign.product.ProductFeignClient;
import com.sun.sunmall.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title HomeServiceImpl
 * @description
 * @create 2023-07-30 21:20
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public HomeContentResult recommendContent() {
        /*品牌和产品在本地缓存中统一处理，有则视为同有，无则视为同无*/
//        final String brandKey = promotionRedisKey.getBrandKey();
//        final boolean allowLocalCache = promotionRedisKey.isAllowLocalCache();
//        /*先从本地缓存中获取推荐内容*/
//        HomeContentResult result = allowLocalCache ?
//                promotionCache.getIfPresent(brandKey) : null;
//        if(result == null){
//            result = allowLocalCache ?
//                    promotionCacheBak.getIfPresent(brandKey) : null;
//        }
//        /*本地缓存中没有*/
//        if(result == null){
//            log.warn("从本地缓存中获取推荐品牌和商品失败，可能出错或禁用了本地缓存[allowLocalCache = {}]",allowLocalCache);
//            result = getFromRemote();
//            if(null != result) {
//                promotionCache.put(brandKey,result);
//                promotionCacheBak.put(brandKey,result);
//            }
//        }
//        /* 处理秒杀内容*/
//        final String secKillKey = promotionRedisKey.getSecKillKey();
//        List<FlashPromotionProduct> secKills = secKillCache.getIfPresent(secKillKey);
//        if(CollectionUtils.isEmpty(secKills)){
//            secKills = secKillCacheBak.getIfPresent(secKillKey);
//        }
//        if(CollectionUtils.isEmpty(secKills)){
//            /*极小的概率出现本地两个缓存同时失效的问题，
//            从远程获取时，只从Redis缓存中获取，不从营销微服务中获取，
//            避免秒杀的流量冲垮营销微服务*/
//            secKills = getSecKillFromRemote();
//            if(!CollectionUtils.isEmpty(secKills)) {
//                secKillCache.put(secKillKey,secKills);
//                secKillCacheBak.put(secKillKey,secKills);
//            }else{
//                /*Redis缓存中也没有秒杀活动信息，此处用一个空List代替，
//                 * 其实可以用固定的图片或信息，作为降级和兜底方案*/
//                secKills = new ArrayList<FlashPromotionProduct>();
//            }
//        }
//        result.setHomeFlashPromotion(secKills);
//        // fixme CMS本次不予实现，设置空集合
//        result.setSubjectList(new ArrayList<CmsSubject>());
//        //获取推荐专题
//        content.setSubjectList(homeDao.getRecommendSubjectList(0,4));
//        return content;
        return null;
    }

    @Override
    public CommonPage recommendProductList(Integer pageSize, Integer pageNum) {
        CommonResult<CommonPage> result = productFeignClient.recommendProductByPage(pageSize,pageNum);
        if (result !=null && 200 == result.getCode()){
            return result.getData();
        }
        return new CommonPage();
    }
}
