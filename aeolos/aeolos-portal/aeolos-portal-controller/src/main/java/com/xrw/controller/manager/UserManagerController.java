package com.xrw.controller.manager;

import com.xrw.common.consts.Const;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/9 15:52
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@RequestMapping("/manager/user")
@Controller
public class UserManagerController {
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = userService.login(username, password);
        if(response.isSuccess()){
            if(response.getData().getRole()==Const.Role.ROLE_ADMIN){
                //说明登录的时管理员
                session.setAttribute(Const.CURRENT_USER,response.getData());
                return response;
            }
            return ServerResponse.createByErrorMessage("不是管理员无法登录");
        }
        return response;
    }
}
