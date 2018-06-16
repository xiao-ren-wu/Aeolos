package com.xrw.portal.service.impl;

import com.xrw.common.consts.Const;
import com.xrw.common.enums.ResponseCode;
import com.xrw.common.utils.BigDecimalUtil;
import com.xrw.common.utils.PropertiesUtil;
import com.xrw.portal.dao.CartMapper;
import com.xrw.portal.dao.ProductMapper;
import com.xrw.portal.pojo.po.Cart;
import com.xrw.portal.pojo.po.Product;
import com.xrw.portal.pojo.vo.CartProductVo;
import com.xrw.portal.pojo.vo.CartVo;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/16 15:22
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked) {
        cartMapper.checkedOrUncheckedProduct(userId,productId,checked);
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
        if(productId==null||count==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart==null){
            //该商品之前没有在购物车中需要新增一个产品的记录
            Cart cartItem = new Cart();
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setQuantity(count);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        }else{
            //该商品已经在购物车中，数量相加即可
            count+=cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);

    }

    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
        if(productId==null||count==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = new Cart();
        cart.setQuantity(count);
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.list(userId);


    }

    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId,String productIds) {
        if(productIds==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //分割商品id并转存到集合中
        String[] split = productIds.split(",");
        List<Integer> productList = new ArrayList<>();
        for(String temp:split){
            Integer productId = new Integer(temp);
            productList.add(productId);
        }

        for(Integer productId:productList){
            Integer flags = cartMapper.deleteByUserIdProductIds(userId, productId);
        }
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer checked) {
        cartMapper.changeAllChecked(userId,checked);
        return this.list(userId);
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        Integer num=cartMapper.selectCartProductCount(userId);
        return ServerResponse.createBySuccess(num);
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        //查询用户在购物车中所有的商品信息
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList=new ArrayList<>();
        //商品总价
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if(cartList!=null){
            for(Cart temp:cartList){
                CartProductVo cartProductVo = new CartProductVo();
                //属性拷贝
                BeanUtils.copyProperties(temp,cartProductVo);
                //通过商品id查询商品信息
                Product product = productMapper.selectByPrimaryKey(temp.getProductId());
                if (product!=null){
                    BeanUtils.copyProperties(product,cartProductVo);
                    //判断库存
                    int buyLimitCount=0;
                    if(product.getStock()>=temp.getQuantity()){
                        //可以添加商品
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else{
                        //限制失败，将有效库存放进去
                        buyLimitCount=product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        temp.setQuantity(buyLimitCount);
                        //重新更改购物车中该商品的库存信息
                        cartMapper.updateByPrimaryKeySelective(temp);
                        //更改vo类中商品数量
                        cartProductVo.setQuantity(buyLimitCount);
                    }
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                }
                //如果上面那个商品被勾选了，要累加在总价中
                if(temp.getChecked()==Const.Cart.CHECKED){
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        cartVo.setCartTotalPrice(cartTotalPrice);
        return cartVo;
    }
    private boolean getAllCheckedStatus(Integer userId){
        if(userId==null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId)==0?true:false;
    }

}
