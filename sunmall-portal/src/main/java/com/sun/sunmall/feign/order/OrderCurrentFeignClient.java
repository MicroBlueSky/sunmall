package com.sun.sunmall.feign.order;

import com.sun.sunmall.bean.OmsOrderDetail;
import com.sun.sunmall.bean.UmsMemberReceiveAddress;
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

    @PostMapping("/list/userOrder")
    CommonResult<List<OmsOrderDetail>> findMemberOrderByMemberIdAndProductId(@RequestParam(value = "memberId", defaultValue = "1") Long memberId,
                                                                             @RequestParam(value = "productId") Long productId);
}
