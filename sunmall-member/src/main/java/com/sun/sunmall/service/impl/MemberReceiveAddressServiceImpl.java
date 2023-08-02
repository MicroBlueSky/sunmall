package com.sun.sunmall.service.impl;

import com.sun.sunmall.bean.UmsMemberReceiveAddress;
import com.sun.sunmall.bean.UmsMemberReceiveAddressExample;
import com.sun.sunmall.mapper.UmsMemberReceiveAddressMapper;
import com.sun.sunmall.service.MemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title MemberReceiveAddressServiceImpl
 * @description
 * @create 2023-07-30 16:00
 */
@Service
public class MemberReceiveAddressServiceImpl implements MemberReceiveAddressService {
    @Autowired
    private UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMemberReceiveAddress> list(long memberId) {
        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        return umsMemberReceiveAddressMapper.selectByExample(example);
    }
}
