package com.xrw.portal.service;

import com.xrw.portal.pojo.po.Shipping;
import com.xrw.portal.pojo.vo.ServerResponse;

import java.util.List;
import java.util.Map;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/16 19:31
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public interface ShippingService {

    /**
     * 新增收货地址
     * @param shipping 收货地址信息
     * @param userId 用户id
     * @return
     */
    ServerResponse<Map> add(Integer userId, Shipping shipping);

    /**
     * 删除用户收货地址
     * @param shippingId
     * @param userId 用户id,加入此字段就是为了防止横向越权
     * @return
     */
    ServerResponse<String> del(Integer userId,Integer shippingId);

    /**
     * 更新用户收货地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse<String> update(Integer userId, Shipping shipping);

    /**
     * 查询具体的收货地址
     * @param shippingId 收货地址Id
     * @param userId 用户ID
     * @return
     */
    ServerResponse<Shipping> select(Integer userId,Integer shippingId);

    /**
     * 收货地址列表
     * @param userId
     * @return
     */
    ServerResponse<List<Shipping>> list(Integer userId);
}
