package com.sun.sunmall.service;

import com.sun.sunmall.bean.PmsComment;
import com.sun.sunmall.bean.PmsCommentReplay;
import com.sun.sunmall.common.api.CommonResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalProductCommentService
 * @description
 * @create 2023-07-30 21:27
 */
public interface PortalProductCommentService {

    /**
     * 获取评论列表
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    CommonResult getCommentList(Long productId, Integer pageNum, Integer pageSize);

    /**
     * 用户评价
     * @param pmsComment
     * @return
     */
    @Transactional
    Integer insertProductComment(PmsComment pmsComment);

    /**
     * 用户评价回复
     * @param replay
     * @return
     */
    @Transactional
    Integer insertCommentReply(PmsCommentReplay replay);
}
