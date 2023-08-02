package com.sun.sunmall.controller;

import com.sun.sunmall.bean.PmsComment;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.domain.PmsCommentParam;
import com.sun.sunmall.service.PortalProductCommentService;
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
@Api(tags = "PortalProductCommentController", description = "商品评论管理")
@RequestMapping("/portal/")
public class PortalProductCommentController {

    @Autowired
    private PortalProductCommentService commentService;

    @ApiOperation("产品评论信息列表")
    @GetMapping(value = "commentList/{productId}")
    public CommonResult<List<PmsCommentParam>> getCommentList(
            @PathVariable Long productId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        return commentService.getCommentList(productId,pageNum,pageSize);
    }

    @PostMapping("/sendComment")
    @ApiOperation("产品发布评论")
    public CommonResult sendProductComment(@RequestBody PmsComment pmsComment){
        Integer result = commentService.insertProductComment(pmsComment);
        if (result> 0) {
            return CommonResult.success(null);
        }else if (result == -1){
            return CommonResult.failed("您没有购买过当前商品,无法评价！");
        }
        return CommonResult.failed();
    }

}
