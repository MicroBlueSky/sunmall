package com.sun.sunmall.controller;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import com.ramostear.captcha.support.CaptchaType;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/sso")
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
public class UmsMemberController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsMemberService umsMemberService;

    @PostMapping("/register")
    @ApiOperation("会员注册")
    public CommonResult register(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String telephone,
                                 @RequestParam String authCode) {
        return umsMemberService.register(username, password, telephone, authCode);
    }

    @ApiOperation("获取验证码图片")
    @GetMapping("/verifyCode")
    public void generateImg(HttpServletRequest request, HttpServletResponse response){
        HappyCaptcha.require(request, response)
                .style(CaptchaStyle.ANIM)
                .type(CaptchaType.ARITHMETIC_ZH)
                .build()
                .finish();
    }

    @GetMapping("/getVerifyCode")
    @ApiOperation("获取验证码")
    public CommonResult getVerifyCode(@RequestParam String telephone){
        return umsMemberService.generateAuthCode(telephone);
    }

}

