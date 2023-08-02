package com.sun.sunmall.service;

import com.sun.sunmall.bean.dto.UserLoginDTO;
import com.sun.sunmall.common.api.CommonResult;

import javax.servlet.http.HttpServletRequest;

public interface UmsMemberService {
    /*
     * 会员注册
     */
    CommonResult register(String username, String password, String telephone, String authCode);

    /*
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);

    CommonResult login(UserLoginDTO dto, HttpServletRequest request);
}
