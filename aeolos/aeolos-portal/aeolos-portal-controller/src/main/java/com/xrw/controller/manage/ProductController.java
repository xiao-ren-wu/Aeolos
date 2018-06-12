package com.xrw.controller.manage;

import com.xrw.common.consts.Const;
import com.xrw.controller.utils.Check;
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
        if(!Check.checkLogin()){
            return ServerResponse.createByErrorMessage("用户未登录，赶紧登录去");
        }
        //使用shiro进行权限验证
        if(!Check.checkRole()){
            return ServerResponse.createByErrorMessage("不是管理员没有权限登录");
        }
        return productService.saveOrUpdate(product);
    }

    @PostMapping(value = "product/setSaleStatus")
    @ResponseBody
    public ServerResponse<String> setSaleStatus(@RequestParam(value = "productId")Integer productId,
                                                @RequestParam(value = "status")Integer status){
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(!Check.checkLogin()){
            return ServerResponse.createByErrorMessage("用户未登录，赶紧登录去");
        }
        //使用shiro进行权限验证
        if(!Check.checkRole()){
            return ServerResponse.createByErrorMessage("不是管理员没有权限登录");
        }
        return productService.setSaleStatus(productId,status);
    }
}
