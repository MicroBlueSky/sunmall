package com.sun.sunmall.dao;

import com.sun.sunmall.model.PmsProduct;
import com.sun.sunmall.model.product.PmsComment;
import com.sun.sunmall.model.product.PmsCommentParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title CommentDao
 * @description
 * @create 2023-09-02 17:32
 */
public interface CommentDao {

    /**
     * @description 添加评论
     */
    void addComment(PmsComment pmsComment);

    /**
     * @description 统计商品评论
     */
    long countCommentByPage(@Param("productId") Long productId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    /**
     * @description 查询商品评论
     */
    List<PmsCommentParam> listCommentByPage(@Param("productId") Long productId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
