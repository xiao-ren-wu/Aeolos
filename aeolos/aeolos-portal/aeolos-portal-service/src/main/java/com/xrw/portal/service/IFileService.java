package com.xrw.portal.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/13 22:53
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public interface IFileService {
    /**
     * 文件上传
     * @param file
     * @param path
     * @return
     */
    String upload(MultipartFile file,String path);
}
