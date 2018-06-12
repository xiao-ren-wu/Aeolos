package com.xrw.portal.service;

import com.xrw.portal.pojo.po.Product;
import com.xrw.portal.pojo.vo.ServerResponse;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/12 15:37
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public interface ProductService {
    /**
     * 更新或者添加新产品
     * @param product 产品对象
     * @return
     */
    ServerResponse<String> saveOrUpdate(Product product);

    /**
     * 更改商品状态
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
}
