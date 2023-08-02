package com.sun.sunmall.bean.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String username;
    private String password;
    private String authCode;
    private String telephone;
}
