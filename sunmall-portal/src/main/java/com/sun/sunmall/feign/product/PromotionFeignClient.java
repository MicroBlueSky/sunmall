package com.sun.sunmall.feign.product;

import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.promotion.domain.FlashPromotionProduct;
import com.sun.sunmall.promotion.domain.HomeContentResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title PromotionFeignClient
 * @description
 * @create 2023-09-02 11:20
 */
@FeignClient(value = "sunmall-product", path = "promotion",contextId = "promotion")
public interface PromotionFeignClient {

    /*推荐内容类型:0->全部；1->品牌；2->新品推荐；3->人气推荐;4->轮播广告*/
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    @ResponseBody
    CommonResult<HomeContentResult> content(@RequestParam(value = "getType") int getType);

    CommonResult<List<FlashPromotionProduct>> getHomeSecKillProductList();
}
