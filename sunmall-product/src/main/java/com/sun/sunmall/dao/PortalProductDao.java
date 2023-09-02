package com.sun.sunmall.dao;

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
}
