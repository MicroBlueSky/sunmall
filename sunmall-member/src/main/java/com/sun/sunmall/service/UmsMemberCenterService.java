package com.sun.sunmall.service;

import com.sun.sunmall.bean.domain.PortalMemberInfo;

/**
 * @title UmsMemberCenterService
 *@description
 *@author Administrator
 *@version 1.0
 *@create 2023-07-30 15:31
 */

public interface UmsMemberCenterService {
    
    /**
     * @description
     * @author
     * @param
     * @throws
     * @return
     * @time
     */
    PortalMemberInfo getMemberInfo(Long memberId);
}
