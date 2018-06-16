package com.xrw.portal.dao;


import com.xrw.portal.pojo.po.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaorenwu
 */
public interface ShippingMapper {

    /**
     * 新增收货地址
     * @param shipping
     * @return
     */
    Integer insert(Shipping shipping);

    /**
     * 删除用户收货地址，加入用户id进行验证，避免横向越权
     * @param userId
     * @param shippingId
     * @return
     */
    Integer deleteAddr(@Param("userId") Integer userId,
                       @Param("shippingId") Integer shippingId);

    /**
     * 更新用户收货地址
     * @param shipping
     * @return
     */
    Integer updateByShipping(Shipping shipping);

    /**
     * 查询具体的收货地址
     * @param userId
     * @param shippingId
     * @return
     */
    Shipping selectByShippingIdUserId(@Param("userId") Integer userId,
                                      @Param("shippingId") Integer shippingId);

    /**
     * 收货地址列表
     * @param userId
     * @return
     */
    List<Shipping> list(Integer userId);
}