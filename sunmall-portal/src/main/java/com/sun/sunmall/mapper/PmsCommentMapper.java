package com.sun.sunmall.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sun.sunmall.bean.PmsComment;

@DS("goods")
public interface PmsCommentMapper {

    int insert(PmsComment record);
}