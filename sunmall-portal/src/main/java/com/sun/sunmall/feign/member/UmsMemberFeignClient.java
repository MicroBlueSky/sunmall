package com.sun.sunmall.feign.member;

import com.sun.sunmall.bean.PortalMemberInfo;
import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.bean.UmsMemberReceiveAddress;
import com.sun.sunmall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title UmsMemberFeignClient
 * @description
 * @create 2023-07-31 22:30
 */
@FeignClient(value = "sunmall-member", path = "/member")
public interface UmsMemberFeignClient {

    @GetMapping("/address/{id}")
    @ResponseBody
    CommonResult<UmsMemberReceiveAddress> getItem(@PathVariable(value = "id") Long id);

    @PostMapping("/center/updateUmsMember")
    CommonResult<String> updateUmsMember(@RequestBody UmsMember member);

    @GetMapping("/center/getMemberInfo")
    @ResponseBody
    CommonResult<PortalMemberInfo> getMemberId();

    @GetMapping("/address/list")
    @ResponseBody
    CommonResult<List<UmsMemberReceiveAddress>> list();
}
