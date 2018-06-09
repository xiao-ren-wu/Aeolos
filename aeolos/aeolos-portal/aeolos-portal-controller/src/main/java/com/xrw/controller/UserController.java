package com.xrw.controller;

import com.xrw.common.consts.Const;
import com.xrw.common.enums.ResponseCode;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/9 10:26
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ServerResponse<User> login(String username,String password,HttpSession session){
        ServerResponse<User> login = userService.login(username, password);
        if(login.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,login.getData());
        }
        return login;
    }
    @PostMapping("/register")
    @ResponseBody
    public ServerResponse<String> register(User user){
        return userService.register(user);
    }

    @GetMapping("/logout")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }
    @GetMapping("/check_valid")
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return userService.checkValid(str,type);
    }
    @GetMapping("/get_user_info")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null) {
            return ServerResponse.createBySuccess(user);
        }else{
            return ServerResponse.createByErrorMessage("用户未登录，无法获取用户当前信息");
        }
    }
    @GetMapping("/forget_get_question")
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return userService.forgetGetQuestion(username);
    }
    @GetMapping("/forget_check_answer")
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return userService.checkAnswer(username,question,answer);
    }
    @PostMapping("/forget_reset_password")
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return userService.forgetResetPasswordToken(username,passwordNew,forgetToken);
    }
    @PostMapping("/reset_password")
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordNew,String passwordOld){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return userService.resetPassword(passwordOld,passwordNew,user);
    }
    @PostMapping("/update_information")
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpSession session,User updateUser){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(null==user){
            return ServerResponse.createBySuccessMessage("用户未登录");
        }
        ServerResponse<User> response = userService.updateUserMsg(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    @GetMapping("/get_information")
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return userService.getUserMsgById(user.getId());
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录");
    }
}
