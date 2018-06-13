package com.xrw.portal.exception;


import com.xrw.portal.pojo.vo.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/13 8:25
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@ControllerAdvice
public class SecurityExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServerResponse<String> handler(Exception e){
        return ServerResponse.createByErrorMessage(e.getMessage());
    }
}
