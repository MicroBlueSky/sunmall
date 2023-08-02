package com.sun.sunmall.mapper;

import com.sun.sunmall.bean.UmsMemberReceiveAddress;
import com.sun.sunmall.bean.UmsMemberReceiveAddressExample;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title UmsMemberReceiveAddressMapper
 * @description
 * @create 2023-07-30 16:01
 */
public interface UmsMemberReceiveAddressMapper {

    List<UmsMemberReceiveAddress> selectByExample(UmsMemberReceiveAddressExample example);
}
