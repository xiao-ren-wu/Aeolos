package com.xrw.portal.service;

import com.xrw.portal.pojo.po.Category;
import com.xrw.portal.pojo.vo.CategoryShowVo;
import com.xrw.portal.pojo.vo.ServerResponse;

import java.util.List;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/11 21:10
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public interface CategoryService {
    /**
     * 根据parentId查找子类节点（平级）
     * @param parentId
     * @return
     */
    ServerResponse<List<Category>> getCategory(Integer parentId);

    /**
     * 添加节点
     * @param parentId 父类节点id
     * @param categoryName 节点名称
     * @return
     */
    ServerResponse<String> addCategory(Integer parentId, String categoryName);

    /**
     * 更新品类节点
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse<String> setCategory(Integer categoryId, String categoryName);

    /**
     * 通过父类id查询所有子节点
     * @param parentId
     * @return
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId);

    /**
     * 树状展示商品类别
     * @param categoryId
     * @return
     */
    ServerResponse<List<CategoryShowVo>> treeShowCategory(Integer categoryId);
}
