package com.sun.sunmall.service;

import com.sun.sunmall.promotion.domain.HomeContentResult;

/**
 * @author Administrator
 * @version 1.0
 * @title HomePromotionService
 * @description
 * @create 2023-09-03 17:43
 */
public interface HomePromotionService {
    /**
     * @description 获取首页推荐品牌和产品
     */
    HomeContentResult content(int getType);
}
