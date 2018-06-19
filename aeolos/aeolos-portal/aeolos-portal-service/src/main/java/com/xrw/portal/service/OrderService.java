package com.xrw.portal.service;

import com.xrw.portal.pojo.vo.OrderProductVo;
import com.xrw.portal.pojo.vo.OrderVo;
import com.xrw.portal.pojo.vo.ServerResponse;

import java.util.List;
import java.util.Map;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/18 12:11
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public interface OrderService {
    /**
     * 支付
     * @param orderNo 订单号
     * @param userId 用户id
     * @param path 图片路径
     * @return
     */
    ServerResponse pay(Long orderNo,Integer userId,String path);

    /**
     * 处理支付宝回调
     * @param requestParams
     * @return
     */
    ServerResponse callback(Map requestParams);

    /**
     * 产看订单状态
     * @param id
     * @param orderNo
     * @return
     */
    ServerResponse<Boolean> queryOrderPayStatus(Integer id, Long orderNo);

    /**
     * 根据购物车已经勾选的商品创建订单
     * @param shippingId 购物车id
     * @param userId 用户id
     * @return
     */
    ServerResponse<OrderVo> create(Integer userId, Integer shippingId);

    /**
     * 取消订单
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse<String> cancel(Integer userId, Long orderNo);

    /**
     * 获取已经勾选的商品详情
     * @param userId
     * @return
     */
    ServerResponse<OrderProductVo> getOrderCartProduct(Integer userId);

    /**
     * 查看订单中某个商品的详情
     * @param id
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> detail(Integer id, Long orderNo);

    /**
     * 查看用户订单列表
     * @param userId
     * @return
     */
    ServerResponse<List<OrderVo>> list(Integer userId);
}
