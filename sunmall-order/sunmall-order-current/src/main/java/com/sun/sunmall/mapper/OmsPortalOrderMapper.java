package com.sun.sunmall.mapper;

import com.sun.sunmall.bean.domain.OmsOrderDetail;
import com.sun.sunmall.common.api.CommonResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title OmsPortalOrderMapper
 * @description
 * @create 2023-08-02 23:51
 */
public interface OmsPortalOrderMapper {

    List<OmsOrderDetail> findMemberOrderByMemberIdAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);
}
