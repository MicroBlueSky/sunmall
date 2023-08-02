package com.sun.sunmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.sun.sunmall.bean.PmsComment;
import com.sun.sunmall.bean.PmsCommentReplay;
import com.sun.sunmall.bean.UmsMember;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.dao.PortalProductCommentDao;
import com.sun.sunmall.feign.member.UmsMemberFeignClient;
import com.sun.sunmall.mapper.PmsCommentMapper;
import com.sun.sunmall.service.PortalProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    private PortalProductCommentDao productCommentDao;
    @Autowired
    private PmsCommentMapper pmsCommentMapper;

    @Autowired
    private UmsMemberFeignClient umsMemberFeignClient;

    @Override
    public CommonResult getCommentList(Long productId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return CommonResult.success(productCommentDao.getCommentList(productId));
    }

    @Override
    public Integer insertProductComment(PmsComment pmsComment) {
        UmsMember umsMember = umsMemberFeignClient.getMemberId().getData();
        //调用订单服务
        Integer integer = productCommentDao.selectUserOrder(umsMember.getId(), pmsComment.getProductId());
        if (integer >0 ){
            pmsComment.setCreateTime(new Date());
            pmsComment.setShowStatus(0);
            pmsComment.setMemberNickName(umsMember.getNickname());
            pmsComment.setMemberIcon(umsMember.getIcon());
            return pmsCommentMapper.insert(pmsComment);
        }
        return -1;
    }

    @Override
    public Integer insertCommentReply(PmsCommentReplay replay) {
        return null;
    }
}
