package com.sun.sunmall.dao;

import com.sun.sunmall.bean.domain.PortalMemberInfo;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalMemberInfoDao
 * @description
 * @create 2023-07-30 15:37
 */
public interface PortalMemberInfoDao {

    /**
     * 查询会员信息
     */
    PortalMemberInfo getMemberInfo(Long memberId);
}
