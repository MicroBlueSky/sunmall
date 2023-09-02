package com.sun.sunmall.feign.product;

import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title ProductFeignClient
 * @description
 * @create 2023-09-02 11:32
 */
@FeignClient(value = "sunmall-product", path = "/product",contextId = "product")
@ResponseBody
public interface ProductFeignClient {

    @GetMapping(value = "/getAllId")
    List<Long> getRecommandBrandList();

    @PostMapping("/listByPage")
    CommonResult<CommonPage> recommendProductByPage(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum);
}
