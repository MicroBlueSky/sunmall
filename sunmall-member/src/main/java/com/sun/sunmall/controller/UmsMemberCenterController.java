package com.sun.sunmall.controller;

import com.sun.sunmall.bean.domain.PortalMemberInfo;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.service.UmsMemberCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 * @title UmsMemberCenterController
 * @description
 * @create 2023-07-31 23:38
 */
@Api(tags = "UmsMemberCenterController",description = "会员中心管理操作#杨过添加")
@RestController
@RequestMapping("/member/center")
public class UmsMemberCenterController {

    @Autowired
    private UmsMemberCenterService umsMemberCenterService;

    @ApiOperation(value = "获取会员详细信息包含会员等级信息",notes = "会员需要被拆分成微服务")
    @GetMapping("/getMemberInfo")
    public CommonResult<PortalMemberInfo> getMemberInfo(@RequestHeader("memberId") long memberId){
        return CommonResult.success(umsMemberCenterService.getMemberInfo(memberId));
    }
}
