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

}