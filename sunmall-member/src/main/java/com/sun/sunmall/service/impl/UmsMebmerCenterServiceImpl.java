package com.sun.sunmall.service.impl;

import com.sun.sunmall.bean.domain.PortalMemberInfo;
import com.sun.sunmall.dao.PortalMemberInfoDao;
import com.sun.sunmall.service.UmsMemberCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @title UmsMebmerCenterServiceImpl
 * @description
 * @create 2023-07-30 15:35
 */
@Service
public class UmsMebmerCenterServiceImpl implements UmsMemberCenterService {
    @Autowired
    private PortalMemberInfoDao portalMemberInfoDao;

    @Override
    public PortalMemberInfo getMemberInfo(Long memberId) {
        return portalMemberInfoDao.getMemberInfo(memberId);
    }
}
