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

    @Select("<script>"+
            "select i.* from oms_order r inner join\n" +
            "        oms_order_item i\n" +
            "        on r.id = i.order_id\n" +
            "        where r.member_id = #{memberId} and i.product_id = #{productId}"+
    "</script>")
    List<OmsOrderDetail> findMemberOrderByMemberIdAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);
}
