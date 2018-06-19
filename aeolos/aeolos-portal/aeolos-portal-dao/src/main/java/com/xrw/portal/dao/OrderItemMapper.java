package com.xrw.portal.dao;


import com.xrw.portal.pojo.po.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaorenwu
 */
public interface OrderItemMapper {
    /**
     * 查询订单详情
     * @param userId 用户id
     * @param orderNo 订单号
     * @return 订单详情
     */
    List<OrderItem> selectByUserIdOrderNo(@Param("userId")Integer userId,
                                          @Param("orderNo")Long orderNo);

    /**
     * 批量插入订单详情
     * @param orderItemList
     * @return
     */
    Integer batchInsert(List<OrderItem> orderItemList);

    /**
     * 通过订单号和用户id获取订单详情表
     * @param orderNo
     * @param userId
     * @return
     */
    List<OrderItem> getByOrderNoUserId(@Param("orderNo") Long orderNo,
                                             @Param("userId") Integer userId);

    /**
     * 管理员查看所有订单
     * @param orderNo
     * @return
     */
    List<OrderItem> getByOrderNo(Long orderNo);
}