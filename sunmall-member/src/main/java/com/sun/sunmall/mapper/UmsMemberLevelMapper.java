package com.sun.sunmall.mapper;

import com.sun.sunmall.bean.UmsMemberLevel;
import com.sun.sunmall.bean.UmsMemberLevelExample;

import java.util.List;

public interface UmsMemberLevelMapper {

    List<UmsMemberLevel> selectByExample(UmsMemberLevelExample example);
}
