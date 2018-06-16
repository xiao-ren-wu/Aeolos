package com.xrw.portal.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @CreateBy IDEA
 * @Description: 购物车中商品的详细信息
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/16 16:21
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Data
public class CartProductVo {
    /**
     * 购物车Id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 购物车中此商品的数量
     */
    private Integer quantity;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品描述
     */
    private String productSubtitle;
    /**
     * 商品主图
     */
    private String productMainImage;
    /**
     * 商品单价
     */
    private BigDecimal productPrice;
    /**
     * 商品状态
     */
    private Integer productStatus;
    /**
     * 单个商品总价
     */
    private BigDecimal productTotalPrice;
    /**
     * 商品库存
     */
    private Integer productStock;
    /**
     * 此商品是否勾选
     */
    private Integer productChecked;
    /**
     * 限制数量的一个返回结果
     */
    private String limitQuantity;
}
