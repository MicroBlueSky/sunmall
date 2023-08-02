package com.sun.sunmall.mapper;

import com.sun.sunmall.bean.UmsMemberLevel;
import com.sun.sunmall.bean.UmsMemberLevelExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsMemberLevelMapper {

    List<UmsMemberLevel> selectByExample(UmsMemberLevelExample example);
}
