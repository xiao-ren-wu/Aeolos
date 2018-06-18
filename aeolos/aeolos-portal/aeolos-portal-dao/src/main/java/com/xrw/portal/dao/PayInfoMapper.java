package com.xrw.portal.dao;


import com.xrw.portal.pojo.po.PayInfo;

/**
 * @author xiaorenwu
 */
public interface PayInfoMapper {

    /**
     * 添加新的支付信息
     * @param payInfo
     * @return
     */
    Integer insert(PayInfo payInfo);
}