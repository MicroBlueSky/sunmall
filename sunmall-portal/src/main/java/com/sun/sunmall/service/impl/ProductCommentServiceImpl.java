package com.sun.sunmall.service.impl;

import com.sun.sunmall.bean.OmsOrderDetail;
import com.sun.sunmall.bean.PmsCommentReplay;
import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.feign.member.UmsMemberFeignClient;
import com.sun.sunmall.feign.order.OrderCurrentFeignClient;
import com.sun.sunmall.feign.product.ProductFeignClient;
import com.sun.sunmall.model.product.PmsComment;
import com.sun.sunmall.service.ProductCommentService;
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
public class ProductCommentServiceImpl implements ProductCommentService {

    @Autowired
    private UmsMemberFeignClient umsMemberFeignClient;
    @Autowired
    private OrderCurrentFeignClient orderCurrentFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public CommonResult getCommentList(Long productId, Integer pageNum, Integer pageSize) {
        CommonResult page = productFeignClient.listComment(productId,pageSize, pageNum);
        return page;
    }

    @Override
    public CommonResult insertProductComment(PmsComment pmsComment) {
        UmsMember umsMember = umsMemberFeignClient.getMemberId().getData();
        //调用订单服务
        List<OmsOrderDetail> orderDetailList = orderCurrentFeignClient.selectUserOrder(umsMember.getId(), pmsComment.getProductId()).getData();
        if (orderDetailList.size()>0){
            pmsComment.setCreateTime(new Date());
            pmsComment.setShowStatus(0);
            //增加评论
            CommonResult commonResult = productFeignClient.addComment(pmsComment);
            return commonResult;
        }else {
            return CommonResult.failed("您没有购买过当前商品,无法评价！");
        }

    }

    @Override
    public Integer insertCommentReply(PmsCommentReplay replay) {
        return null;
    }
}
