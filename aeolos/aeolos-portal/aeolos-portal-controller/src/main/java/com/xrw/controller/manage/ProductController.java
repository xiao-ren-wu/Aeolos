package com.xrw.controller.manage;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.xrw.common.utils.PropertiesUtil;
import com.xrw.portal.pojo.po.Product;
import com.xrw.portal.pojo.vo.ProductDetailVo;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.IFileService;
import com.xrw.portal.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/12 15:21
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

@RequestMapping("manage")
@Controller
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private IFileService iFileService;

    @PostMapping(value = "/product/save")
    @ResponseBody
    public ServerResponse<String> save(Product product){
        return productService.saveOrUpdate(product);
    }

    @PostMapping(value = "product/setSaleStatus")
    @ResponseBody
    public ServerResponse<String> setSaleStatus(@RequestParam(value = "productId")Integer productId,
                                                @RequestParam(value = "status")Integer status){
        return productService.setSaleStatus(productId,status);
    }
    @GetMapping(value = "product/detail")
    @ResponseBody
    public ServerResponse<ProductDetailVo> manageGetProductDetail(Integer productId){
        return productService.getProductDetail(productId);
    }

    @GetMapping("/list")
    @ResponseBody
    public ServerResponse<PageInfo> getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return productService.getProductList(pageNum,pageSize);
    }

    @GetMapping("/search")
    @ResponseBody
    public ServerResponse<PageInfo> productSearch(String productName,Integer productId,
                                                  @RequestParam(value = "pageNum")Integer pageNum,
                                                  @RequestParam(value = "pageSize")Integer pageSize){
        return productService.searchProduct(productName,productId,pageNum,pageSize);
    }

    /**
     * 文件上传模块
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }

}
