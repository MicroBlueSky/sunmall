package com.sun.sunmall.service.impl;

import com.sun.sunmall.bean.OmsOrderDetail;
import com.sun.sunmall.bean.PmsComment;
import com.sun.sunmall.bean.PmsCommentReplay;
import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.feign.member.UmsMemberFeignClient;
import com.sun.sunmall.feign.order.OrderCurrentFeignClient;
import com.sun.sunmall.service.PortalProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalProductCommentServiceImpl
 * @description
 * @create 2023-07-30 21:27
 */
@Service
public class PortalProductCommentServiceImpl implements PortalProductCommentService {

    @Autowired
    private UmsMemberFeignClient umsMemberFeignClient;
    @Autowired
    private OrderCurrentFeignClient orderCurrentFeignClient;

    @Override
    public CommonResult getCommentList(Long productId, Integer pageNum, Integer pageSize) {

        return CommonResult.success(null);
    }

    @Override
    public Integer insertProductComment(PmsComment pmsComment) {
        UmsMember umsMember = umsMemberFeignClient.getMemberId().getData();
        //调用订单服务
        List<OmsOrderDetail> orderDetailList = orderCurrentFeignClient.findMemberOrderByMemberIdAndProductId(umsMember.getId(), pmsComment.getProductId()).getData();
        if (orderDetailList.size()>0){
            pmsComment.setCreateTime(new Date());
            pmsComment.setShowStatus(0);
            pmsComment.setMemberNickName(umsMember.getNickname());
            pmsComment.setMemberIcon(umsMember.getIcon());
            return -1;
        }else {
            return -1;
        }
    }

    @Override
    public Integer insertCommentReply(PmsCommentReplay replay) {
        return null;
    }
}
