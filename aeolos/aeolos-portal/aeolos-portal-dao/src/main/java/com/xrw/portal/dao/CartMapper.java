package com.xrw.portal.dao;


import com.xrw.portal.pojo.po.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaorenwu
 */
public interface CartMapper {
    /**
     * 通过用户id和商品id查找之前用户在购物车添加该商品的信息
     * @param userId 用户id
     * @param productId 商品id
     * @return 购物车
     */
    Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    /**
     * 在购物车中添加新商品
     * @param cartItem 新商品信息
     * @return 标志
     */
    Integer insert(Cart cartItem);

    /**
     * 更新用户的购物车商品信息
     * @param record 更新的商品信息
     * @return 标志
     */
    int updateByPrimaryKeySelective(Cart record);

    /**
     * 通过用户id查找用户购物车中所有的商品
     * @param userId 用户id
     * @return 用户购物车中所有商品信息
     */
    List<Cart> selectCartByUserId(Integer userId);

    /**
     * 通过用户id查询该用户购物车中所有的商品是否是全部勾选
     * @param userId 用户id
     * @return
     */
    Integer selectCartProductCheckedStatusByUserId(Integer userId);

    /**
     * 删除购物车中的商品
     * @param userId 用户id
     * @param productId 产品id
     * @return
     */
    Integer deleteByUserIdProductIds(@Param("userId") Integer userId,
                                     @Param("productId") Integer productId);

    /**
     * 更改用户购物者的勾选状态
     * @param userId
     * @param checked
     */
    void changeAllChecked(@Param("userId") Integer userId,
                          @Param("checked") Integer checked);

    /**
     * 勾选
     * @param userId
     * @param productId
     * @param checked
     */
    void checkedOrUncheckedProduct(@Param("userId") Integer userId,
                                   @Param("productId") Integer productId,
                                   @Param("checked") Integer checked);

    /**
     * 获取购物车中所有的产品数量
     * @param userId
     * @return
     */
    Integer selectCartProductCount(Integer userId);
}