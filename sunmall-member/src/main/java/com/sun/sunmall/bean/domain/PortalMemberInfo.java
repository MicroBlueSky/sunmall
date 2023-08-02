package com.sun.sunmall.bean.domain;

import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.bean.UmsMemberLevel;
import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalMemberInfo
 * @description
 * @create 2023-07-30 15:33
 */
@Data
public class PortalMemberInfo extends UmsMember {
    private UmsMemberLevel umsMemberLevel;
}
