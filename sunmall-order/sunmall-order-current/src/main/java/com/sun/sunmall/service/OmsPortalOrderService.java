package com.sun.sunmall.service;

import com.sun.sunmall.bean.domain.OmsOrderDetail;
import com.sun.sunmall.common.api.CommonResult;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title OmsPortalOrderService
 * @description
 * @create 2023-08-02 23:48
 */
public interface OmsPortalOrderService {

    CommonResult<List<OmsOrderDetail>> findMemberOrderByMemberIdAndProductId(Long memberId, Long productId);
}
