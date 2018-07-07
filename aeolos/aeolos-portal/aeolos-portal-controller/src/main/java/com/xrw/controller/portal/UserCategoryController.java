package com.xrw.controller.portal;

import com.xrw.common.utils.PropertiesUtil;
import com.xrw.portal.pojo.po.Category;
import com.xrw.portal.pojo.vo.CategoryShowVo;
import com.xrw.portal.pojo.vo.CategoryVo;
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
 * @CreateDate: 2018/6/23 12:53
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Controller
@CrossOrigin
@RequestMapping("/category")
public class UserCategoryController {
    @Resource
    private CategoryService categoryService;


    @GetMapping("/get_category")
    @ResponseBody
    public ServerResponse<CategoryVo> getChildrenParallelCategory(
            @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        ServerResponse<List<Category>> category = categoryService.getChildrenParallelCategory(categoryId);
        List<Category> categoryList = category.getData();
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setCategoryList(categoryList);
        categoryVo.setImgHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServerResponse.createBySuccess(categoryVo);
    }

    @GetMapping("/tree_show_category")
    @ResponseBody
    public ServerResponse<List<CategoryShowVo>> treeShowCategory(
            @RequestParam(value = "categoryId",defaultValue = "0")Integer categoryId){
        return categoryService.treeShowCategory(categoryId);
    }
}
