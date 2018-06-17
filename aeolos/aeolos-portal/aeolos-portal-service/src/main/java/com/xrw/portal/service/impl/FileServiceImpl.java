package com.xrw.portal.service.impl;

import com.google.common.collect.Lists;
import com.xrw.common.utils.FTPUtil;
import com.xrw.portal.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/13 22:54
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {
    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //获取文件拓展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        //创建文件在服务器上的名字
        String uploadFileName=UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始时文件上传，上传文件的文件名称：{}，上传的路径是：{}，新文件名称：{}",fileName,path,uploadFileName);

        System.out.println("那就这样吧");
        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.mkdirs();
            fileDir.setWritable(true);
            System.out.println("创建文件夹");
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到FTP服务器上

            //删除在Tomcat下的文件
            targetFile.delete();


        } catch (IOException e) {
            log.error("文件上传异常",e);
            return null;
        }
        return targetFile.getName();

    }
}
