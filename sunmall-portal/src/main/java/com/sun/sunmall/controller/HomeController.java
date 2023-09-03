package com.sun.sunmall.controller;

import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.promotion.domain.HomeContentResult;
import com.sun.sunmall.service.CacheManageService;
import com.sun.sunmall.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private CacheManageService cacheManageService;

    @ApiOperation("首页内容页信息展示")
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<HomeContentResult> content() {
        HomeContentResult contentResult = cacheManageService.recommendContent();
        return CommonResult.success(contentResult);
    }

    @ApiOperation("分页获取推荐商品")
    @RequestMapping(value = "/recommendProductList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<CommonPage> recommendProductList(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage page = homeService.recommendProductList(pageSize, pageNum);
        return CommonResult.success(page);
    }
}
