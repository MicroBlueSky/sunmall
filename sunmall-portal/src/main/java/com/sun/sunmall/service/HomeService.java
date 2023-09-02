package com.sun.sunmall.service;

import com.sun.sunmall.bean.PmsProduct;
import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.domain.HomeContentResult;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title HomeService
 * @description
 * @create 2023-07-30 21:10
 */
public interface HomeService {

    /*处理首页推荐品牌和商品内容*/
    HomeContentResult recommendContent();

    /**
     * @description 分页获取推荐商品
     */
    CommonPage recommendProductList(Integer pageSize, Integer pageNum);
}
