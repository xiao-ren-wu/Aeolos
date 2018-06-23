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
import java.util.Map;
import java.util.Random;

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
    public ServerResponse<String> save(Product product,
                                       @RequestParam(value = "upload_file1",required = false) MultipartFile file1,
                                       @RequestParam(value = "upload_file2",required = false) MultipartFile file2,
                                       @RequestParam(value = "upload_file3",required = false) MultipartFile file3,
                                       @RequestParam(value = "upload_file4",required = false) MultipartFile file4,
                                       @RequestParam(value = "upload_file5",required = false) MultipartFile file5,
                                       @RequestParam(value = "upload_file6",required = false) MultipartFile file6,
                                       @RequestParam(value = "upload_file7",required = false) MultipartFile file7,
                                       @RequestParam(value = "upload_file8",required = false) MultipartFile file8
                                       ){
        String path ="upload";

        String targetFileName1 = iFileService.upload(file1,path);
        String targetFileName2 = iFileService.upload(file2,path);
        String targetFileName3 = iFileService.upload(file3,path);
        String targetFileName4 = iFileService.upload(file4,path);

        String subImages = targetFileName1 + "," +
                targetFileName2 + "," +
                targetFileName3 + "," +
                targetFileName4 + ",";
        product.setSubImages(subImages);

        String targetFileName5 = iFileService.upload(file5,path);
        String targetFileName6 = iFileService.upload(file6,path);
        String targetFileName7 = iFileService.upload(file7,path);
        String targetFileName8 = iFileService.upload(file8,path);

        String detail = targetFileName5 + "," +
                targetFileName6 + "," +
                targetFileName7 + "," +
                targetFileName8 + ",";
        product.setDetail(detail);

        product.setStatus(1);
        Random rand = new Random();
        int stock = rand.nextInt(1000)+10;
        product.setStock(stock);
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
     */
    @PostMapping("upload")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file1",required = false) MultipartFile file1){
        String path ="upload";
        String targetFileName = iFileService.upload(file1,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",url);
        return ServerResponse.createBySuccess(fileMap);
    }

}
