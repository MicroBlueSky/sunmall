package com.sun.sunmall.service.impl;

import com.sun.sunmall.bean.domain.OmsOrderDetail;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.mapper.OmsPortalOrderMapper;
import com.sun.sunmall.service.OmsPortalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title OmsPortalOrderServiceImpl
 * @description
 * @create 2023-08-02 23:50
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {

    @Autowired
    private OmsPortalOrderMapper omsPortalOrderMapper;

    @Override
    public CommonResult<List<OmsOrderDetail>> findMemberOrderByMemberIdAndProductId(Long memberId, Long productId) {
        List<OmsOrderDetail> list = omsPortalOrderMapper.findMemberOrderByMemberIdAndProductId(memberId,productId);
        return CommonResult.success(list);
    }
}
