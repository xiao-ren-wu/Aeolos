package com.xrw.controller.portal;

import com.xrw.common.consts.Const;
import com.xrw.common.enums.ResponseCode;
import com.xrw.portal.pojo.po.Shipping;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.ShippingService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/16 19:30
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Controller
@CrossOrigin
@RequestMapping("/shipping/")
public class ShippingController {
    @Resource
    private ShippingService shippingService;

    /**
     * 新增收货地址
     * @param shipping
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public ServerResponse<Map> add(Shipping shipping){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return shippingService.add(user.getId(),shipping);
    }

    /**
     * 删除收货地址
     * @param shippingId
     * @return
     */
    @ResponseBody
    @GetMapping("/del")
    public ServerResponse<String> del(Integer shippingId){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.del(user.getId(),shippingId);
    }
    @ResponseBody
    @PostMapping("/update")
    public ServerResponse<String>  update(Shipping shipping){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return shippingService.update(user.getId(),shipping);
    }
    @ResponseBody
    @GetMapping("/select")
    public ServerResponse<Shipping> select(Integer shippingId){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.select(user.getId(),shippingId);
    }

    @ResponseBody
    @GetMapping("/list")
    public ServerResponse<List<Shipping>> list(){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.list(user.getId());
    }
}
