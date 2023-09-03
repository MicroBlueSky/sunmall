package com.sun.sunmall.service;

import com.sun.sunmall.common.api.CommonPage;

/**
 * @author Administrator
 * @version 1.0
 * @title HomeService
 * @description
 * @create 2023-07-30 21:10
 */
public interface HomeService {

    /**
     * @description 分页获取推荐商品
     */
    CommonPage recommendProductList(Integer pageSize, Integer pageNum);
}
