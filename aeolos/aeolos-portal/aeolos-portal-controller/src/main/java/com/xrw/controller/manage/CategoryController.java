package com.xrw.controller.manage;

import com.xrw.common.consts.Const;
import com.xrw.common.enums.ResponseCode;
import com.xrw.portal.pojo.po.Category;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.CategoryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/12 8:30
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@RequestMapping("/manage")
@Controller
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 获取品类子节点
     * @param parentId 父类id
     * @return
     */
    @GetMapping("/get_category")
    @ResponseBody
    public ServerResponse<List<Category>> getCategory(
            @RequestParam(value = "categoryId", defaultValue = "0") Integer parentId) {
        return categoryService.getCategory(parentId);
    }
    @PostMapping("add_category")
    @ResponseBody
    public ServerResponse<String> addCategory(
            @RequestParam(value = "parentId", defaultValue="0")Integer parentId,String categoryName
    ){
        //从session中获取当前用户信息，判断该用户是否是管理员身份
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录，赶紧登录去");
        }
        if(user.getRole()!=Const.Role.ROLE_ADMIN){
            return ServerResponse.createByErrorMessage("不是管理员没有权限登录");
        }
        return categoryService.addCategory(parentId,categoryName);
    }
    @PostMapping("set_category")
    @ResponseBody
    public ServerResponse<String> setCategory(@RequestParam("categoryId")Integer categoryId,@RequestParam("categoryName")String categoryName){
        //获取session
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }
        if(user.getRole()!=Const.Role.ROLE_ADMIN){
            return ServerResponse.createByErrorMessage("当前用户不是管理员，没有权限登录");
        }
        return categoryService.setCategory(categoryId,categoryName);
    }
    @GetMapping("get_children_parallel_category")
    @ResponseBody
    public ServerResponse<List<Category>> getChildrenParallelCategory(
            @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        return categoryService.getChildrenParallelCategory(categoryId);
    }

}
