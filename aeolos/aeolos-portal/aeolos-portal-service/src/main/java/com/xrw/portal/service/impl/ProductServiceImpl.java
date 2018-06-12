package com.xrw.portal.service.impl;

import com.xrw.portal.dao.ProductMapper;
import com.xrw.portal.pojo.po.Product;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Override
    public ServerResponse<String> saveOrUpdate(Product product){
        if(product==null){
            return ServerResponse.createByErrorMessage("参数传递异常");
        }
        if(StringUtils.isNotBlank(product.getSubImages())){
            String[] subImageArray = product.getSubImages().split(",");
            if(subImageArray.length > 0){
                product.setMainImage(subImageArray[0]);
            }
        }

        if(product.getId() != null){
            int rowCount = productMapper.updateByPrimaryKey(product);
            if(rowCount > 0){
                return ServerResponse.createBySuccess("更新产品成功");
            }
            return ServerResponse.createBySuccess("更新产品失败");
        }else{
            int rowCount = productMapper.save(product);
            if(rowCount > 0){
                return ServerResponse.createBySuccess("新增产品成功");
            }
            return ServerResponse.createBySuccess("新增产品失败");
        }
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        //先查询商品是否存在
        Integer checkNum = productMapper.checkProduct(productId);
        if(checkNum<1){
            return ServerResponse.createByErrorMessage("商品不存在");
        }
        //搞事情
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        Integer updateNum = productMapper.updateByPrimaryKey(product);
        if(updateNum>0){
            return ServerResponse.createBySuccess("更新成功");
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }
}
