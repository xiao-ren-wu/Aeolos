package com.xrw.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xrw.common.consts.Const;
import com.xrw.common.enums.ResponseCode;
import com.xrw.common.utils.DateTimeUtil;
import com.xrw.common.utils.PropertiesUtil;
import com.xrw.portal.dao.CategoryMapper;
import com.xrw.portal.dao.ProductMapper;
import com.xrw.portal.pojo.po.Category;
import com.xrw.portal.pojo.po.Product;
import com.xrw.portal.pojo.vo.ProductDetailVo;
import com.xrw.portal.pojo.vo.ProductListVo;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.CategoryService;
import com.xrw.portal.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryService categoryService;


    @Override
    public ServerResponse<List<ProductListVo>> getProductByKeywordCategory(String keyword, Integer categoryId) {
        if(StringUtils.isBlank(keyword)&&categoryId==null){
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();
        if(categoryId!=null){
            Category category = categoryMapper.findCategoryNodeMsg(categoryId);
            if(category==null&&StringUtils.isBlank(keyword)){
                //没有该分类，并且还没有关键字，返回一个空的结果集，不报错
                List<ProductListVo> productListVos=new ArrayList<>();
                return ServerResponse.createBySuccess(productListVos);
            }
            //根据类别Id递归找到所属的子节点
            List<Category> list = categoryService.getChildrenParallelCategory(categoryId).getData();
            for(Category temp:list){
                categoryIdList.add(temp.getId());
            }
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        //给前端包装数据
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product : productList){
            ProductListVo productListVo = new ProductListVo();
            BeanUtils.copyProperties(product,productListVo);
            productListVoList.add(productListVo);
        }

        return ServerResponse.createBySuccess(productListVoList);
    }

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

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        //属性拷贝
        BeanUtils.copyProperties(product,productDetailVo);
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        //查找父级节点
        Category category = categoryMapper.findCategoryNodeMsg(product.getCategoryId());
        if(category==null){
            productDetailVo.setParentCategoryId(0);
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        /*
         * 分页插件使用步骤：
         *  1.startPage--start
         *  2.填充自己的SQL
         *  3.pageHelper收尾
         */
        PageHelper.startPage(pageNum,pageSize);
        List<Product> list =  productMapper.selectList();
        List<ProductListVo> productListVoArrayList = new ArrayList<>();
        for(Product temp:list){
            ProductListVo listVo = new ProductListVo();
            BeanUtils.copyProperties(temp,listVo);
            productListVoArrayList.add(listVo);
        }
        PageInfo pageResult=new PageInfo(productListVoArrayList);
        pageResult.setList(productListVoArrayList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName=new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList=productMapper.selectByNameAndProductId( productName,productId);
        List<ProductListVo> productListVoArrayList = new ArrayList<>();
        for(Product temp:productList){
            ProductListVo listVo = new ProductListVo();
            BeanUtils.copyProperties(temp,listVo);
            productListVoArrayList.add(listVo);
        }
        PageInfo pageResult=new PageInfo(productListVoArrayList);
        pageResult.setList(productListVoArrayList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<ProductDetailVo> getUserProductDetail(Integer productId) {
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null||product.getStatus()!=1){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        //属性拷贝
        BeanUtils.copyProperties(product,productDetailVo);
        ArrayList<String> subImageList = new ArrayList<>();
        String subImage=product.getSubImages();
        String[] sub = subImage.split(",");
        boolean b = Collections.addAll(subImageList, sub);
        productDetailVo.setSubImagesList(subImageList);
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        //查找父级节点
        Category category = categoryMapper.findCategoryNodeMsg(product.getCategoryId());
        if(category==null){
            productDetailVo.setParentCategoryId(0);
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return ServerResponse.createBySuccess(productDetailVo);
    }


}
