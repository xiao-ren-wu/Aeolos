package com.xrw.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.xrw.common.consts.Const;
import com.xrw.common.enums.ResponseCode;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
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
    @ResponseBody
    @GetMapping("alipay_callback")
    public Object alipayCallback(HttpServletRequest request){
        Map requestParams = request.getParameterMap();
        for(Iterator iter=requestParams.keySet().iterator();iter.hasNext();){
            String name=(String)iter.next();
            String[] values=(String[])requestParams.get(name);
            String valueStr = "";
            for(int i=0;i<values.length;i++){
                valueStr = (i==values.length-1)?valueStr+values[i]:valueStr+values[i]+",";
            }
            requestParams.put(name,valueStr);
        }
        log.info("支付宝回调，sign：{},trade_status:{},参数：{}",requestParams.get("sign"),
                requestParams.get("trade_status"),
                requestParams.toString());
        //非常重要，验证回调的重要性，是不是支付宝发过来的，并且还要避免重复通知
        requestParams.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(requestParams,
                    Configs.getAlipayPublicKey(),
                    "utf-8",
                    Configs.getSignType());
            if(!alipayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求，验证不通过");
            }

        } catch (AlipayApiException e) {
            log.error("支付宝验证回调异常",e);
        }
        ServerResponse callback = orderService.callback(requestParams);
        if(callback.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }else {
           return Const.AlipayCallback.RESPONSE_FAILED;
        }
    }
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
