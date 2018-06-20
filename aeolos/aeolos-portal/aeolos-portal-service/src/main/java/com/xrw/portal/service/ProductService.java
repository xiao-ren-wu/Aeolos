package com.xrw.portal.service;

import com.github.pagehelper.PageInfo;
import com.xrw.portal.pojo.po.Product;
import com.xrw.portal.pojo.vo.ProductDetailVo;
import com.xrw.portal.pojo.vo.ProductListVo;
import com.xrw.portal.pojo.vo.ServerResponse;

import java.util.List;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/12 15:37
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public interface ProductService {
    /**
     * 用户模块，商品列表展示
     * @param keyword 关键字
     * @param categoryId 商品类别id
     * @return 商品列表，分页展示
     */
    ServerResponse<List<ProductListVo>> getProductByKeywordCategory(String keyword, Integer categoryId);

    /**
     * 更新或者添加新产品
     * @param product 产品对象
     * @return
     */
    ServerResponse<String> saveOrUpdate(Product product);

    /**
     * 更改商品状态
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * 后台接口，根据productId查找商品详情
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    /**
     * 后台展示商品列表
     * @param pageNum 第几页
     * @param pageSize 每页展示商品数量
     * @return
     */
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    /**
     * 动态排序
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    /**
     * 普通用户商品详情，和后台的不同是如果商品status=1不展示
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVo> getUserProductDetail(Integer productId);
}
