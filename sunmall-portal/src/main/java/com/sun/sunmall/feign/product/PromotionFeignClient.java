package com.sun.sunmall.feign.product;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Administrator
 * @version 1.0
 * @title PromotionFeignClient
 * @description
 * @create 2023-09-02 11:20
 */
@FeignClient(value = "sunmall-product", path = "promotion",contextId = "promotion")
public interface PromotionFeignClient {
}
