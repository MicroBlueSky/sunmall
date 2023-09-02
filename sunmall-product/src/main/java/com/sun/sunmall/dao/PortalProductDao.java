package com.sun.sunmall.dao;

import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.model.PmsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title ProductDao
 * @description
 * @create 2023-09-01 22:56
 */
public interface PortalProductDao {

    /**
     * 查找出所有的产品ID
     * @return
     */
    List<Long> getAllProductId();

    /**
     * @description 查找出所有的产品
     */
    List<PmsProduct> listProductByPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    /**
     * @description 统计所有的产品
     */
    long countProductByPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
