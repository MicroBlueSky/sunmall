package com.sun.sunmall.service.impl;

import com.sun.sunmall.constant.ConstantPromotion;
import com.sun.sunmall.mapper.HomeBrandMapper;
import com.sun.sunmall.model.SmsHomeAdvertise;
import com.sun.sunmall.model.product.PmsBrand;
import com.sun.sunmall.promotion.domain.HomeContentResult;
import com.sun.sunmall.service.HomePromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title HomePromotionServiceImpl
 * @description
 * @create 2023-09-03 17:44
 */
@Service
@Slf4j
public class HomePromotionServiceImpl implements HomePromotionService {

    @Autowired
    private HomeBrandMapper homeBrandMapper;

    @Override
    public HomeContentResult content(int getType) {
        HomeContentResult result = new HomeContentResult();
        if(ConstantPromotion.HOME_GET_TYPE_ALL == getType
                ||ConstantPromotion.HOME_GET_TYPE_BARND == getType){
            //获取推荐品牌
            result.setBrandList(getRecommendBrand());
        }
        if(ConstantPromotion.HOME_GET_TYPE_ALL == getType
                ||ConstantPromotion.HOME_GET_TYPE_NEW == getType){
            getRecommendProducts(result);
        }
        if(ConstantPromotion.HOME_GET_TYPE_ALL == getType
                ||ConstantPromotion.HOME_GET_TYPE_HOT == getType){
            getHotProducts(result);
        }
        if(ConstantPromotion.HOME_GET_TYPE_ALL == getType
                ||ConstantPromotion.HOME_GET_TYPE_AD == getType){
            //获取首页广告
            result.setAdvertiseList(getHomeAdvertiseList());
        }
        return result;
    }

    private List<PmsBrand> getRecommendBrand() {
//        List<Long> smsHomeBrandIds = homeBrandMapper.selectBrandIdByExample();
//        List<PmsBrand> recommendBrandList = homeBrandMapper.getRecommandBrandList(smsHomeBrandIds);
//        return recommendBrandList;
        return null;
    }

    /*获取人气推荐产品*/
    private void getRecommendProducts(HomeContentResult result){
//        final String recProductKey = promotionRedisKey.getRecProductKey();
//        List<PmsProduct> recommendProducts = redisOpsExtUtil.getListAll(recProductKey, PmsProduct.class);
//        if(CollectionUtils.isEmpty(recommendProducts)){
//            redisDistrLock.lock(promotionRedisKey.getDlRecProductKey(),promotionRedisKey.getDlTimeout());
//            try {
//                PageHelper.startPage(0,ConstantPromotion.HOME_RECOMMEND_PAGESIZE,"sort desc");
//                SmsHomeRecommendProductExample example2 = new SmsHomeRecommendProductExample();
//                example2.or().andRecommendStatusEqualTo(ConstantPromotion.HOME_PRODUCT_RECOMMEND_YES);
//                List<Long> recommendProductIds = smsHomeRecommendProductMapper.selectProductIdByExample(example2);
//                recommendProducts = pmsProductClientApi.getProductBatch(recommendProductIds);
//                redisOpsExtUtil.putListAllRight(recProductKey,recommendProducts);
//            } finally {
//                redisDistrLock.unlock(promotionRedisKey.getDlRecProductKey());
//            }
//            log.info("人气推荐商品信息存入缓存，键{}" ,recProductKey);
//            result.setHotProductList(recommendProducts);
//        }else{
//            log.info("人气推荐商品信息已在缓存，键{}" ,recProductKey);
//            result.setHotProductList(recommendProducts);
//        }
    }

    /*获取新品推荐产品*/
    private void getHotProducts(HomeContentResult result){
//        f
    }

    /*获取轮播广告*/
    private List<SmsHomeAdvertise> getHomeAdvertiseList() {
//        final String homeAdvertiseKey = promotionRedisKey.getHomeAdvertiseKey();
//        List<SmsHomeAdvertise> smsHomeAdvertises =
//                redisOpsExtUtil.getListAll(homeAdvertiseKey, SmsHomeAdvertise.class);
//        if(CollectionUtils.isEmpty(smsHomeAdvertises)){
//            redisDistrLock.lock(promotionRedisKey.getDlHomeAdvertiseKey(),promotionRedisKey.getDlTimeout());
//            try {
//                SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
//                Date now = new Date();
//                example.createCriteria().andTypeEqualTo(ConstantPromotion.HOME_ADVERTISE_TYPE_APP)
//                        .andStatusEqualTo(ConstantPromotion.HOME_ADVERTISE_STATUS_ONLINE)
//                        .andStartTimeLessThan(now).andEndTimeGreaterThan(now);
//                example.setOrderByClause("sort desc");
//                smsHomeAdvertises = advertiseMapper.selectByExample(example);
//                redisOpsExtUtil.putListAllRight(homeAdvertiseKey,smsHomeAdvertises);
//            } finally {
//                redisDistrLock.unlock(promotionRedisKey.getDlHomeAdvertiseKey());
//            }
//            log.info("轮播广告存入缓存，键{}" ,homeAdvertiseKey);
//            return smsHomeAdvertises;
//        }else{
//            log.info("轮播广告已在缓存，键{}" ,homeAdvertiseKey);
//            return smsHomeAdvertises;
//        }
        return null;
    }
}
