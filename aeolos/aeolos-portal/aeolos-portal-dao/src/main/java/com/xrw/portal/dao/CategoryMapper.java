package com.xrw.portal.dao;


import com.xrw.portal.pojo.po.Category;
import com.xrw.portal.pojo.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaorenwu
 */
public interface CategoryMapper {

    /**
     * 根据parentID查找子类节点（平级）
     * @param parentId
     * @return
     */
    List<Category> getCategory(Integer parentId);

    /**
     * 查询是否有该父类节点
     * @param parentId 父类节点
     * @return
     */
    Integer findCategoryNode(Integer parentId);

    /**
     * 检查该品类节点是否存在在数据库中
     * @param parentId
     * @param categoryName
     * @return
     */
    Integer checkCategory(@Param(value = "parentId") Integer parentId,
                          @Param(value = "categoryName") String categoryName);

    /**
     * 添加节点
     * @param parentId 父类节点
     * @param categoryName 节点名称
     * @return
     */
    Integer addCategory(@Param(value = "parentId") Integer parentId,
                        @Param(value = "categoryName") String categoryName);

    /**
     * 检查该品类节点是否存在在数据库中
     * @param categoryId
     * @param categoryName
     * @return
     */
    Integer findCategory(@Param("categoryId") Integer categoryId,
                         @Param("categoryName") String categoryName);

    /**
     * 更新类别名称
     * @param category
     * @return
     */
    Integer setCategory(Category category);


    /**
     * 根据id查找节点详细信息
     * @param categoryId
     * @return
     */
    Category findCategoryNodeMsg(Integer categoryId);
}
