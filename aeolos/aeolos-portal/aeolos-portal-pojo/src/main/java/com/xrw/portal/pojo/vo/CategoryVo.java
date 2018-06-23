package com.xrw.portal.pojo.vo;

import com.xrw.portal.pojo.po.Category;
import lombok.Data;

import java.util.List;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/23 18:34
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Data
public class CategoryVo {
    private List<Category> categoryList;
    private String imgHost;
}
