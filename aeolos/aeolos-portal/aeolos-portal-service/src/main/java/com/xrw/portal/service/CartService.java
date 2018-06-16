package com.xrw.portal.service;

import com.xrw.portal.pojo.vo.CartVo;
import com.xrw.portal.pojo.vo.ServerResponse;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/16 15:22
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public interface CartService {
    /**
     * 勾选
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    /**
     * 添加购物车商品
     * @param userId 用户ID
     * @param productId 商品ID
     * @param count 商品数量
     * @return 状态
     */
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    /**
     * 更新购物车，增加商品数量，减少商品数量
     * @param userId 用户id
     * @param productId 商品id
     * @param count 商品数量
     * @return
     */
    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    /**
     * 删除购物车中的商品，可能用户一次勾选多个，所以约定商品id用，隔开
     * @param productIds 商品id们
     * @param userId 用户id
     * @return
     */
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    /**
     * 查看购物车
     * @param userId 用户id
     * @return
     */
    ServerResponse<CartVo> list(Integer userId);

    /**
     * 全选或者全不选
     * @param userId
     * @param checked
     * @return
     */
    ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer checked);

    /**
     * 获取所有商品数量
     * @param userId
     * @return
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
