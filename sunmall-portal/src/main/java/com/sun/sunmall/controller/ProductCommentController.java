package com.sun.sunmall.controller;

import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.domain.PmsCommentParam;
import com.sun.sunmall.model.product.PmsComment;
import com.sun.sunmall.service.ProductCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalProductCommentController
 * @description
 * @create 2023-07-30 21:23
 */
@RestController
@Api(tags = "ProductCommentController", description = "商品评论管理")
@RequestMapping("/portal/")
public class ProductCommentController {

    @Autowired
    private ProductCommentService commentService;

    @ApiOperation("产品评论信息列表")
    @GetMapping(value = "commentList")
    public CommonResult<List<PmsCommentParam>> getCommentList(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        return commentService.getCommentList(productId,pageNum,pageSize);
    }

    @PostMapping("/sendComment")
    @ApiOperation("产品发布评论")
    public CommonResult sendProductComment(@RequestBody PmsComment pmsComment){
        CommonResult result = commentService.insertProductComment(pmsComment);
        return result;
    }

}
