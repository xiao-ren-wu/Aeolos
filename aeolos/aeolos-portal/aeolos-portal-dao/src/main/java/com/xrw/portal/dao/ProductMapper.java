package com.xrw.portal.dao;

import com.xrw.portal.pojo.po.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaorenwu
 */
public interface ProductMapper {

    /**
     * 后台查询所有商品
     * @return 商品列表
     */
    List<Product> selectList();

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

    /**
     * 根据商品ID查询商品详情
     * @param productId
     * @return
     */
    Product selectByPrimaryKey( Integer productId);

    /**
     * 动态搜索
     * @param productName
     * @param productId
     * @return
     */
    List<Product> selectByNameAndProductId(
            @Param("productName")String productName,
            @Param("productId")Integer productId);


    /**
     * 根据商品和商品类别id查询商品列表
     * @param productName 商品名称，模糊
     * @return 商品列表
     */
    List<Product> selectByName(@Param("productName")String productName);

    /**
     * 通过类别查找商品
     * @param categoryId
     * @return
     */
    Product selectByCategoryId(Integer categoryId);

    /**
     * 通过订单id查询商品库存
     * @param productId 商品id
     * @return 商品库存
     */
    Integer selectStockByProductId(@Param("productId") Integer productId);
}