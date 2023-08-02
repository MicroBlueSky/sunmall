package com.sun.sunmall.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sun.sunmall.domain.PmsCommentParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalProductCommentDao
 * @description
 * @create 2023-07-30 21:30
 */
@DS("goods")
public interface PortalProductCommentDao {

    List<PmsCommentParam> getCommentList(Long productId);

    Integer selectUserOrder(@Param("userId") Long userId, @Param("productId") Long productId);
}
