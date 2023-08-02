package com.sun.sunmall.mapper;

import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.bean.UmsMemberExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsMemberMapper {

    List<UmsMember> selectByExample(UmsMemberExample example);

    void insert(UmsMember umsMember);
}
