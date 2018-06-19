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
}