package com.sun.sunmall.service.impl;

import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.feign.product.ProductFeignClient;
import com.sun.sunmall.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CommonPage recommendProductList(Integer pageSize, Integer pageNum) {
        CommonResult<CommonPage> result = productFeignClient.recommendProductByPage(pageSize,pageNum);
        if (result !=null && 200 == result.getCode()){
            return result.getData();
        }
        return new CommonPage();
    }
}
