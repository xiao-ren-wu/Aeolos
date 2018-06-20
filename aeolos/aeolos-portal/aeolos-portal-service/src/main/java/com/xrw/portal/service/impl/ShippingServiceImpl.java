package com.xrw.portal.service.impl;

import com.xrw.portal.dao.ShippingMapper;
import com.xrw.portal.pojo.po.Shipping;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.ShippingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/16 19:32
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Service
public class ShippingServiceImpl implements ShippingService {

    @Resource
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse<Map> add(Integer userId,Shipping shipping) {
        if(shipping==null){
            return ServerResponse.createByErrorMessage("参数传递错误");
        }
        shipping.setUserId(userId);
        Integer insert = shippingMapper.insert(shipping);
        if(insert>0){
            Map result = new HashMap(1);
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功",result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    @Override
    public ServerResponse<String> del(Integer userId,Integer shippingId) {
        if(shippingId==null){
            return ServerResponse.createByErrorMessage("参数传递错误");
        }
        Integer resultCount = shippingMapper.deleteAddr(userId, shippingId);
        if(resultCount>0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    /**
     * 更新收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse<String> update(Integer userId, Shipping shipping) {
        if(shipping==null){
            return ServerResponse.createByErrorMessage("参数传递错误");
        }
        shipping.setUserId(userId);
        Integer insert = shippingMapper.updateByShipping(shipping);
        if(insert>0){
            return ServerResponse.createByErrorMessage("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        if(shippingId==null){
            return ServerResponse.createByErrorMessage("参数传递错误");
        }
        Shipping shipping =shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if(shipping==null){
            return ServerResponse.createByErrorMessage("没有查到收货地址");
        }
        return ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse<List<Shipping>> list(Integer userId) {
        List<Shipping> list = shippingMapper.list(userId);
        if(list==null){
            return ServerResponse.createByErrorMessage("该用户没有设置设置收货地址");
        }
        return ServerResponse.createBySuccess(list);
    }


}
