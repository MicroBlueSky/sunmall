package com.sun.sunmall.controller;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import com.ramostear.captcha.support.CaptchaType;
import com.sun.sunmall.bean.dto.UserLoginDTO;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
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
    @ResponseBody
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
                .style(CaptchaStyle.IMG)
                .type(CaptchaType.ARITHMETIC)
                .build()
                .finish();
    }

    @GetMapping("/getAuthCode")
    @ApiOperation("获取验证码")
    @ResponseBody
    public CommonResult getAuthCode(@RequestParam String telephone){
        return umsMemberService.generateAuthCode(telephone);
    }

    @PostMapping("/login")
    @ApiOperation("会员登录")
    @ResponseBody
    public CommonResult login(UserLoginDTO dto, HttpServletRequest request){
        return umsMemberService.login(dto, request);
    }
}

