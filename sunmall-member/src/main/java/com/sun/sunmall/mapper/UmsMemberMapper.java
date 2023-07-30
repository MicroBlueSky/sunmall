package com.sun.sunmall.mapper;

import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.bean.UmsMemberExample;

import java.util.List;

public interface UmsMemberMapper {

    List<UmsMember> selectByExample(UmsMemberExample example);

    void insert(UmsMember umsMember);
}
