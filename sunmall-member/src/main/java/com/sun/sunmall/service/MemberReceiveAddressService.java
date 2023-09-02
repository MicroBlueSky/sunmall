package com.sun.sunmall.service;

import com.sun.sunmall.bean.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title MemberReceiveAddressService
 * @description 用户地址管理Service
 * @create 2023-07-30 15:59
 */
public interface MemberReceiveAddressService {

    /**
     * 返回当前用户的收货地址
     */
    List<UmsMemberReceiveAddress> list(long memberId);
}
