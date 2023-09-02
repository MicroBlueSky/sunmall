package com.sun.sunmall.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sun.sunmall.bean.SmsFlashPromotionProductRelation;

@DS("promotion")
public interface SmsFlashPromotionProductRelationMapper {

    SmsFlashPromotionProductRelation selectByPrimaryKey(Long id);
}