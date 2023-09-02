package com.sun.sunmall.feign.order;

import com.sun.sunmall.bean.OmsOrderDetail;
import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.model.product.PmsComment;
import com.sun.sunmall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title OrderCurrentFeignClient
 * @description
 * @create 2023-08-31 22:02
 */
@FeignClient(value = "sunmall-order-current", path = "order")
public interface OrderCurrentFeignClient {

    @PostMapping("/selectUserOrder")
    CommonResult<List<OmsOrderDetail>> selectUserOrder(@RequestParam(value = "memberId", defaultValue = "1") Long memberId,
                                                                             @RequestParam(value = "productId") Long productId);

}
