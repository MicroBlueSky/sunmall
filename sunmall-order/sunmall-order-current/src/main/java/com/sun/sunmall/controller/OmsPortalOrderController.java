package com.sun.sunmall.controller;

import com.sun.sunmall.bean.domain.OmsOrderDetail;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.service.OmsPortalOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title OmsOrderController
 * @description
 * @create 2023-08-02 23:41
 */
@RestController
@RequestMapping("/order/")
@Api(tags = "OmsPortalOrderController",description = "订单管理")
public class OmsPortalOrderController {
    @Autowired
    private OmsPortalOrderService omsPortalOrderService;

    @ApiOperation("用户订单查询")
    @RequestMapping(value = "/selectUserOrder",method = RequestMethod.POST)
    public CommonResult<List<OmsOrderDetail>> selectUserOrder(@RequestParam(value = "memberId", defaultValue = "1") Long memberId,
                                                                  @RequestParam(value = "productId") Long productId){
        return omsPortalOrderService.findMemberOrderByMemberIdAndProductId(memberId,productId);
    }

}
