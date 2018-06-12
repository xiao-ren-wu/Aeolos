package com.xrw.controller.manage;

import com.xrw.common.consts.Const;
import com.xrw.portal.pojo.po.Product;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.ProductService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/12 15:21
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

@RequestMapping("manage")
@Controller
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping(value = "/product/save")
    @ResponseBody
    public ServerResponse<String> save(Product product){
        //从session中获取当前用户，判断是否是管理员
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("没有登录，请先登录");
        }
        Integer role = user.getRole();
        if(role!=1){
            return ServerResponse.createByErrorMessage("当前用户不是管理员，没有权限");
        }
        return productService.saveOrUpdate(product);
    }

    @PostMapping(value = "product/setSaleStatus")
    @ResponseBody
    public ServerResponse<String> setSaleStatus(@RequestParam(value = "productId")Integer productId,
                                                @RequestParam(value = "status")Integer status){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("没有登录，请先登录");
        }
        Integer role = user.getRole();
        if(role!=1){
            return ServerResponse.createByErrorMessage("当前用户不是管理员，没有权限");
        }
        return productService.setSaleStatus(productId,status);
    }
}
