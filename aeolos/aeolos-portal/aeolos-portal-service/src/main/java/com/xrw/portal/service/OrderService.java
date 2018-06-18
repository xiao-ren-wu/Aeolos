package com.xrw.portal.service;

import com.xrw.portal.pojo.vo.ServerResponse;

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
}
