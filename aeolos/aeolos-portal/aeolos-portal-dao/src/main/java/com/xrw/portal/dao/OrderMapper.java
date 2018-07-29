package com.xrw.portal.dao;


import com.xrw.portal.pojo.po.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaorenwu
 */
public interface OrderMapper {
    /**
     * 通过用户id和订单号查询订单的相信信息
     * @param userId
     * @param orderNo
     * @return
     */
    Order selectByUserIdAndOrderNo(@Param("userId")Integer userId,
                                   @Param("orderNo")Long orderNo);

    /**
     * 查找订单是否存在
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(Long orderNo);

    /**
     * 更新订单信息
     * @param order
     * @return
     */
    Integer updateByPrimaryKey(Order order);

    /**
     * 插入订单
     * @param order
     * @return
     */
    Integer insert(Order order);

    /**
     * 通过用户ID查询用户订单
     * @param userId
     * @return
     */
    List<Order> selectByUserId(Integer userId);

    /**
     * 关闭订单
     * @param status 订单状态
     * @param date 订单日期
     * @return
     */
    List<Order> selectOrderStatusByCreateTime(@Param("status") Integer status,
                                              @Param("date") String date);

    /**
     * 通过商品id查询商品库存，这里一定要使用Integer,因为int无法为null
     * 考虑到很多商品已经删除的情况
     * 使用到了悲观锁
     * @param id 商品id
     * @return 商品库存，如果商品不存在，返回null
     */
    Integer selectStockByProductId(@Param("id") Integer id);

    /**
     * 通过订单id关闭订单，更改订单状态
     * @param id 订单id
     */
    void closeOrderByOrderId(@Param("id") Integer id);
}