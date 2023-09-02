package com.sun.sunmall.service.impl;

import com.sun.sunmall.common.api.CommonPage;
import com.sun.sunmall.dao.PortalProductDao;
import com.sun.sunmall.model.PmsProduct;
import com.sun.sunmall.service.PortalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @title PortalProductServiceImpl
 * @description
 * @create 2023-09-02 15:05
 */
@Service
public class PortalProductServiceImpl  implements PortalProductService {
    @Autowired
    private PortalProductDao portalProductDao;

    @Override
    public CommonPage listProductByPage(Integer pageNum, Integer pageSize) {
        CommonPage page = new CommonPage();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        long total = portalProductDao.countProductByPage(pageNum, pageSize);
        page.setTotal(total);
        if (total > 0) {
            List<PmsProduct> list = portalProductDao.listProductByPage(pageNum, pageSize);
            page.setList(list);
        }
        return page;
    }

    @Override
    public List<Long> getRecommandBrandList() {
        return portalProductDao.getAllProductId();
    }
}
