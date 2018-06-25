package com.xrw.controller.portal;

import com.xrw.common.consts.Const;
import com.xrw.common.enums.ResponseCode;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.CartVo;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.CartService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/14 15:03
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@CrossOrigin
@Controller
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;

    /**
     * 向购物车中添加商品
     * @param count 商品数量
     * @param productId 商品id
     * @return emmm
     */
    @ResponseBody
    @PostMapping("add")
    public ServerResponse<CartVo> add(Integer count, Integer productId){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.add(user.getId(),productId,count);
    }

    /**
     * 更新购物车中的商品数量
     * @param count
     * @param productId
     * @return
     */
    @ResponseBody
    @PostMapping("update")
    public ServerResponse<CartVo> update(Integer count, Integer productId){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.update(user.getId(),productId,count);
    }

    /**
     * 删除购物车里面的商品们
     * @param productIds
     * @return
     */
    @ResponseBody
    @PostMapping("delete_product")
    public ServerResponse<CartVo> deleteProduct(String productIds){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.deleteProduct(user.getId(),productIds);
    }

    /**
     * 查看购物车
     * @return 购物车列表
     */
    @ResponseBody
    @GetMapping("list")
    public ServerResponse<CartVo> list(){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.list(user.getId());
    }

    /**
     * 勾选全部购物车中商品
     * @return
     */
    @ResponseBody
    @GetMapping("select_all")
    public ServerResponse<CartVo> selectAll(){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),Const.Cart.CHECKED);
    }
    @ResponseBody
    @GetMapping("un_select_all")
    public ServerResponse<CartVo> unSelectAll(){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),Const.Cart.UN_CHECKED);
    }

    /**
     * 勾选购物者中某个商品
     * @param productId
     * @return
     */
    @RequestMapping("select")
    @ResponseBody
    public ServerResponse<CartVo> select(Integer productId){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

    /**
     * 取消选中购物车中的某个商品
     * @param productId
     * @return
     */
    @RequestMapping("un_select")
    @ResponseBody
    public ServerResponse<CartVo> unSelect(Integer productId){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
    }

    /**
     * 查询购物车中的商品数量
     * @return
     */
    @RequestMapping("get_cart_product_count")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createBySuccess(0);
        }
        return cartService.getCartProductCount(user.getId());
    }

}
