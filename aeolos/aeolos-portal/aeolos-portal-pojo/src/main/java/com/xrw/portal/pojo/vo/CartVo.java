package com.xrw.portal.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/16 16:23
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Data
public class CartVo {
    /**
     * 购物车中商品列表
     */
    private List<CartProductVo> cartProductVoList;
    /**
     * 购物车总的钱数
     */
    private BigDecimal cartTotalPrice;
    /**
     * 是否已经都勾选
     */
    private Boolean allChecked;
    /**
     * 图片服务器ip
     */
    private String imageHost;
}
