package com.xrw.portal.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiaorenwu
 */
@Data
public class ProductListVo {

    private Integer id;
    private Integer categoryId;

    private String name;
    private String subtitle;
    private String mainImage;
    private BigDecimal price;

    private Integer status;

    private String imageHost;
}
