package com.sun.sunmall.controller;

import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.dao.PortalProductDao;
import com.sun.sunmall.model.PmsProduct;
import com.sun.sunmall.model.product.PmsComment;
import com.sun.sunmall.service.PortalProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalProductController
 * @description
 * @create 2023-09-01 22:55
 */
@RestController
@Api(tags = "PortalProductController", description = "商品查询查看")
@RequestMapping("/product/")
public class PortalProductController {
    @Autowired
    private PortalProductService portalProductService;

    @ApiOperation(value = "查询所有商品id")
    @GetMapping(value = "/getAllId")
    public List<Long> getRecommandBrandList(){
        return portalProductService.getRecommandBrandList();
    }

    @ApiOperation(value = "查询所有商品列表")
    @PostMapping(value = "/listByPage")
    public CommonResult<CommonPage> listProductByPage(Integer pageNum, Integer pageSize){
        CommonPage page = portalProductService.listProductByPage(pageNum, pageSize);
        return CommonResult.success(page);
    }

    @ApiOperation(value = "添加品论")
    @PostMapping(value = "/addComment")
    public CommonResult addComment(@RequestBody PmsComment pmsComment){
        CommonResult result = portalProductService.addComment(pmsComment);
        return CommonResult.success(result);
    }

    @ApiOperation(value = "查询商品评论列表")
    @PostMapping(value = "/commentByPage")
    public CommonResult commentByPage(@RequestParam("productId") Long productId,
                                      @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        CommonPage page = portalProductService.commentByPage(productId,pageSize,pageNum);
        return CommonResult.success(page);
    }
}
