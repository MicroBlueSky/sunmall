package com.sun.sunmall.service;

import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.model.product.PmsComment;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalProductService
 * @description
 * @create 2023-09-02 15:04
 */
public interface PortalProductService {
    /**
     * @description 分页查询商品
     */
    CommonPage listProductByPage(Integer pageNum, Integer pageSize);

    List<Long> getRecommandBrandList();

    CommonResult addComment(PmsComment pmsComment);

    CommonPage commentByPage(Long productId, Integer pageSize, Integer pageNum);
}
