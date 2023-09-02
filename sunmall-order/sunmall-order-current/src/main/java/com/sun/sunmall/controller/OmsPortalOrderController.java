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
@RequestMapping("/order/")
@RestController
@Api(tags = "OmsPortalOrderController",description = "订单管理")
public class OmsPortalOrderController {
    @Autowired
    private OmsPortalOrderService omsPortalOrderService;

    @ApiOperation("用户订单查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "用户ID", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "status", value = "订单状态:0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭",
                    allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")})
    @RequestMapping(value = "list/userOrder",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<OmsOrderDetail>> findMemberOrderByMemberIdAndProductId(@RequestParam(value = "memberId", defaultValue = "1") Long memberId,
                                                                  @RequestParam(value = "productId") Long productId){
        return omsPortalOrderService.findMemberOrderByMemberIdAndProductId(memberId,productId);
    }

}
