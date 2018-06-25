package com.xrw.controller.manage;

import com.xrw.controller.utils.Check;
import com.xrw.portal.aop.AdminOnly;
import com.xrw.portal.pojo.po.Category;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.CategoryService;
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
@CrossOrigin
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
    @AdminOnly
    public ServerResponse<List<Category>> getCategory(
            @RequestParam(value = "categoryId", defaultValue = "0") Integer parentId) {
        if(!Check.checkLogin()){
            return ServerResponse.createByErrorMessage("用户未登录，赶紧登录去");
        }
        //使用shiro进行权限验证
        if(!Check.checkRole()){
            return ServerResponse.createByErrorMessage("不是管理员没有权限登录");
        }
        return categoryService.getCategory(parentId);
    }
    @PostMapping("add_category")
    @ResponseBody
    public ServerResponse<String> addCategory(
            @RequestParam(value = "parentId", defaultValue="0")Integer parentId,String categoryName
    ){

        if(Check.checkLogin()){
            return ServerResponse.createByErrorMessage("用户未登录，赶紧登录去");
        }
        //使用shiro进行权限验证
        if(!Check.checkRole()){
            return ServerResponse.createByErrorMessage("不是管理员没有权限登录");
        }
        return categoryService.addCategory(parentId,categoryName);
    }
    @PostMapping("set_category")
    @ResponseBody
    public ServerResponse<String> setCategory(@RequestParam("categoryId")Integer categoryId,@RequestParam("categoryName")String categoryName){
        if(Check.checkLogin()){
            return ServerResponse.createByErrorMessage("用户未登录，赶紧登录去");
        }
        //使用shiro进行权限验证
        if(!Check.checkRole()){
            return ServerResponse.createByErrorMessage("不是管理员没有权限登录");
        }
        return categoryService.setCategory(categoryId,categoryName);
    }
    @GetMapping("get_children_parallel_category")
    @ResponseBody
    public ServerResponse<List<Category>> getChildrenParallelCategory(
            @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        if(!Check.checkLogin()){
            return ServerResponse.createByErrorMessage("用户未登录，赶紧登录去");
        }
        //使用shiro进行权限验证
        if(!Check.checkRole()){
            return ServerResponse.createByErrorMessage("不是管理员没有权限登录");
        }
        return categoryService.getChildrenParallelCategory(categoryId);
    }


}
