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
     * @param categoryIdList 商品类别列表
     * @return 商品列表
     */
    List<Product> selectByNameAndCategoryIds(@Param("productName")String productName,
                                             @Param("categoryIdList")List<Integer> categoryIdList);
}