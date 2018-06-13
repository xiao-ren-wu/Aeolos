package com.xrw.portal.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/13 14:51
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Data
public class ProductDetailVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;

    private String createTime;
    private String updateTime;


    private String imageHost;
    private Integer parentCategoryId;
}
