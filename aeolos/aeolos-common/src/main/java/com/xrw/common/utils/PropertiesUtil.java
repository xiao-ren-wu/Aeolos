package com.xrw.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/13 15:26
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Slf4j
public class PropertiesUtil {

    private static Properties properties;

    static {
        properties = new Properties();
        String fileName = "xrw.properties";
        try {
            PropertiesUtil.properties.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("[配置文件读取异常]error={}",e.getMessage());
        }
    }
    public static String getProperty(String key){
        String value = properties.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }
    public static String getProperty(String key,String defaultValue){
        String property = getProperty(key);
        if(property==null){
            return defaultValue;
        }
        return property;
    }
}
