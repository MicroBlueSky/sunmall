package com.sun.sunmall.controller;

import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.domain.HomeContentResult;
import com.sun.sunmall.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 * @title HomeController
 * @description 首页内容管理Controller
 * @create 2023-07-30 21:00
 */
@RestController
@Api(tags = "HomeController", description = "首页内容管理")
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @ApiOperation("首页内容页信息展示")
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<HomeContentResult> content() {
//        HomeContentResult contentResult = homeService.recommendContent();
//        homeService.cmsContent(contentResult);
//        return CommonResult.success(contentResult);
        return CommonResult.success(null);
    }
}
