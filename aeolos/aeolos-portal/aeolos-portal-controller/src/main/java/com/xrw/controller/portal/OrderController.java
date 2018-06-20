package com.xrw.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.xrw.common.consts.Const;
import com.xrw.common.enums.ResponseCode;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.OrderProductVo;
import com.xrw.portal.pojo.vo.OrderVo;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/18 12:07
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Slf4j
@RequestMapping("/order")
@Controller
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     * @param shippingId 收货地址id
     */
    @RequestMapping("/create")
    @ResponseBody
    public ServerResponse<OrderVo> create(Integer shippingId){
        //判断用户是否登录
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.create(user.getId(),shippingId);
    }

    /**
     * 取消订单
     * @param orderNo
     */
    @ResponseBody
    @GetMapping("/cancel")
    public ServerResponse<String> cancel(Long orderNo){
        //判断用户是否登录
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.cancel(user.getId(),orderNo);
    }

    /**
     * 获取购物车中已经选中的商品详情
     */
    @ResponseBody
    @RequestMapping("/get_order_cart_product")
    public ServerResponse<OrderProductVo> getOrderCartProduct(){
        //判断用户是否登录
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.getOrderCartProduct(user.getId());
    }

    /**
     * 获取订单详情
     */
    @ResponseBody
    @RequestMapping("/detail")
    public ServerResponse<OrderVo> detail(Long orderNo){
        //判断用户是否登录
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.detail(user.getId(),orderNo);
    }

    /**
     * 用户获取订单列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServerResponse<List<OrderVo>> list(){
        //判断用户是否登录
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.list(user.getId());
    }

    /**
     * 支付
     * @param orderNo 订单号
     */
    @ResponseBody
    @GetMapping("/pay")
    public ServerResponse pay(Long orderNo){
        //判断用户是否登录
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String path ="upload";
        return orderService.pay(orderNo,user.getId(),path);
    }

    /**
     * 支付宝回调
     * @param request 获取回调信息
     */
    @ResponseBody
    @PostMapping("/alipay_callback")
    public Object alipayCallback(HttpServletRequest request){

        Map<String,String> params = Maps.newHashMap();

        Map requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {

                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("支付宝回调,sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        //非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.

        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());

            if(!alipayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求,验证不通过,再恶意请求我就报警找网警了");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝验证回调异常",e);
        }

        //todo 验证各种数据


        //
        ServerResponse serverResponse = orderService.callback(params);

        if(serverResponse.isSuccess()){

            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    /**
     * 查看订单支付状态
     * @param orderNo 订单号
     */
    @ResponseBody
    @RequestMapping("query_order_pay_status")
    public ServerResponse<Boolean> queryOrderPayStatus(Long orderNo){
        //判断用户是否登录
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        ServerResponse<Boolean> response = orderService.queryOrderPayStatus(user.getId(), orderNo);
        if(response.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }else{
            return ServerResponse.createBySuccess(false);
        }
    }

}
