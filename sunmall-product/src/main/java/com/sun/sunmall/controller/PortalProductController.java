package com.sun.sunmall.controller;

import com.sun.sunmall.dao.PortalProductDao;
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
@RequestMapping("/portalProduct/")
public class PortalProductController {
    @Autowired
    private PortalProductDao portalProductDao;

    @ApiOperation(value = "查询所有商品id")
    @GetMapping(value = "/getAllId")
    public List<Long> getRecommandBrandList(){
        return portalProductDao.getAllProductId();
    }
}
