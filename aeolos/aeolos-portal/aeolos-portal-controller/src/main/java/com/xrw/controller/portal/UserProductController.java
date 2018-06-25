package com.xrw.controller.portal;

import com.xrw.portal.pojo.vo.ProductDetailVo;
import com.xrw.portal.pojo.vo.ProductListVo;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/14 12:59
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

@Controller
@CrossOrigin
@RequestMapping("/product")
public class UserProductController {
    @Resource
    private ProductService productService;

    /**
     * 商品详情
     * @param productId
     * @return
     */
    @GetMapping("/detail")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return productService.getUserProductDetail(productId);
    }

    /**
     * 商品展示
     * @param keyword 关键字
     * @param categoryId 商品类别id
     * @return 商品列表，分页展示
     */
    @GetMapping("list")
    @ResponseBody
    public ServerResponse<List<ProductListVo>> list(@RequestParam(value = "keyword",required = false)String keyword,
                                                    @RequestParam(value = "categoryId",required = false)Integer categoryId){
        return productService.getProductByKeywordCategory(keyword,categoryId);
    }

}
