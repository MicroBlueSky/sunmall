package com.sun.sunmall.controller;

import com.sun.sunmall.bean.SmsFlashPromotionProductRelation;
import com.sun.sunmall.common.api.CommonResult;
import com.sun.sunmall.mapper.SmsFlashPromotionProductRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：图灵学院
 **/
@RestController
@RequestMapping("/stock")
public class StockManageController {
    @Autowired
    private SmsFlashPromotionProductRelationMapper flashPromotionProductRelationMapper;

    /**
     * 查询当前产品的库存
     * @param productId
     * @return
     */
    @RequestMapping(value = "/selectStock",method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResult<Integer> selectStock(@RequestParam("productId") Long productId, @RequestParam("flashPromotionRelationId") Long flashPromotionRelationId){
        SmsFlashPromotionProductRelation miaoshaStock = flashPromotionProductRelationMapper.selectByPrimaryKey(flashPromotionRelationId);
        if(ObjectUtils.isEmpty(miaoshaStock)){
            return CommonResult.failed("不存在该秒杀商品！");
        }
        return CommonResult.success(miaoshaStock.getFlashPromotionCount());
    }
}
