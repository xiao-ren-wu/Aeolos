package com.xrw.portal.dao;

import com.xrw.portal.pojo.po.Product;

/**
 * @author xiaorenwu
 */
public interface ProductMapper {


    /**
     * 添加商品
     * @param product
     * @return
     */
    Integer save(Product product);

    /**
     * 修改商品信息
     * @param product
     * @return
     */
    int updateByPrimaryKey(Product product);

    /**
     * 检查商品是否存在
     * @param id
     * @return
     */
    Integer checkProduct(Integer id);
}