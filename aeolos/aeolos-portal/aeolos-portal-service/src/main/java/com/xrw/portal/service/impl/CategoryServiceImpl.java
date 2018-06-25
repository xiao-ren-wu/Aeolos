package com.xrw.portal.service.impl;

import com.xrw.common.utils.PropertiesUtil;
import com.xrw.portal.dao.CategoryMapper;
import com.xrw.portal.pojo.po.Category;
import com.xrw.portal.pojo.vo.CategoryShowVo;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @CreateBy IDEAscription: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/11 21:11
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse<List<Category>> getCategory(Integer parentId) {
        if(parentId==null){
            return ServerResponse.createByErrorMessage("参数传递异常");
        }
        List<Category> list = categoryMapper.getCategory(parentId);
        if(list.size()==0){
            return ServerResponse.createByErrorMessage("未找到该品类");
        }
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<String> addCategory(Integer parentId, String categoryName) {
        if(parentId==null||categoryName==null){
            return ServerResponse.createByErrorMessage("参数不合法");
        }
        //去数据库中查询是否有该父类节点
        Integer parentCount = categoryMapper.findCategoryNode(parentId);
        if(parentCount==0){
            return ServerResponse.createByErrorMessage("没有找到对应的父类节点");
        }

        //查询是否之前就存在要添加的节点
        Integer count = categoryMapper.checkCategory(parentId, categoryName);
        if(count>0){
            return ServerResponse.createByErrorMessage("该节点已经存在");
        }
        //添加节点
        Integer addCount = categoryMapper.addCategory(parentId,categoryName);
        if(addCount>0){
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse<String> setCategory(Integer categoryId, String categoryName) {
        if(categoryId==null||categoryName==null){
            return ServerResponse.createByErrorMessage("参数传递异常");
        }
        //查找数据库中是否存在该节点
        Integer categoryCount = categoryMapper.findCategory(categoryId, categoryName);
        if(categoryCount<1){
            return ServerResponse.createByErrorMessage("要修改的节点不存在");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        Integer updateCount = categoryMapper.setCategory(category);
        if(updateCount>0){
            return ServerResponse.createBySuccess("更新品类名称成功");
        }
        return ServerResponse.createByErrorMessage("更新品类失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId) {
        if(parentId==null){
            return ServerResponse.createByErrorMessage("参数传递错误");
        }
        HashSet<Category> categories = new HashSet<>();
        //递归查询子节点
        Set<Category> categories1 = getChildrenParallelCategoryCore(parentId, categories);
        ArrayList<Category> categories2 = new ArrayList<>(categories1);
        return ServerResponse.createBySuccess(categories2);
    }

    @Override
    public ServerResponse<List<CategoryShowVo>> treeShowCategory(Integer categoryId) {
        if(categoryId==null){
            return ServerResponse.createByErrorMessage("参数传递错误");
        }
        CategoryShowVo categoryShowVo = new CategoryShowVo();
        treeShowCategoryCore(categoryId,categoryShowVo);
        List<CategoryShowVo> categoryShowVoList = categoryShowVo.getCategoryShowVoList();
        return ServerResponse.createBySuccess(categoryShowVoList);
    }

    private void treeShowCategoryCore(Integer categoryId,CategoryShowVo categoryShowVo) {
        //根据parentId查找子类别
        List<Category> category = categoryMapper.getCategory(categoryId);
        if(category==null){
            return ;
        }
        //属性拷贝，类型转换
        for (Category categoryTemp : category) {
            CategoryShowVo categoryShowVoTemp = new CategoryShowVo();
            BeanUtils.copyProperties(categoryTemp,categoryShowVoTemp);
            //将没有拷贝好的属性赋值
            categoryShowVoTemp.setCategoryImgUrl(PropertiesUtil.getProperty("ftp.server.http.prefix")+categoryTemp.getCategoryImg());
            List<CategoryShowVo> categoryShowVoList = categoryShowVo.getCategoryShowVoList();
            categoryShowVoList.add(categoryShowVoTemp);
            //将当前节点的id作为父类id查找他的子节点
            this.treeShowCategoryCore(categoryShowVoTemp.getId(),categoryShowVoTemp);
        }
    }

    private Set<Category> getChildrenParallelCategoryCore(Integer categoryId,Set<Category> set){
        Category msg = categoryMapper.findCategoryNodeMsg(categoryId);
        if(msg!=null){
            set.add(msg);
        }
        List<Category> category = categoryMapper.getCategory(categoryId);
        for(Category temp:category){
            getChildrenParallelCategoryCore(temp.getId(),set);
        }
        return set;
    }

}
