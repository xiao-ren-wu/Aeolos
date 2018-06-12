package com.xrw.controller.manage;

import com.xrw.common.consts.Const;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@CrossOrigin
@RequestMapping("/admin")
@Controller
public class UserManagerController {
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse<User> login(@RequestParam(value = "username") String username,
                                      @RequestParam(value = "password") String password) {
        ServerResponse<User> response = userService.loginByShiro(username, password);
        if (response.isSuccess()) {
            if (response.getData().getRole() == Const.Role.ROLE_ADMIN) {
                Session session = SecurityUtils.getSubject().getSession();
                //说明登录的时管理员
                session.setAttribute(Const.CURRENT_USER, response.getData());
                return response;
            }
            return ServerResponse.createByErrorMessage("不是管理员无法登录");
        }
        return response;
    }
}
