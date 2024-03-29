package com.sun.sunmall.mapper;

import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.bean.UmsMemberExample;
import com.sun.sunmall.bean.domain.PortalMemberInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmsMemberMapper {

    List<UmsMember> selectByExample(UmsMemberExample example);

    void insert(UmsMember umsMember);

    /**
     * 查询会员信息
     */
    PortalMemberInfo getMemberInfo(Long memberId);
}
