package com.xrw.portal.service;

import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/9 10:31
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
public interface UserService {
    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 用户注册
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 检查用户是否有效
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str,String type);

    /**
     * 忘记密码，通过用户名获取问题
     * @param username
     * @return
     */
    ServerResponse<String> forgetGetQuestion(String username);

    /**
     * 根据用户名，问题，答案，验证答案的正确性
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username,String question,String answer);

    /**
     * 重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetResetPasswordToken(String username,String passwordNew,String forgetToken);

    /**
     * 登录状态更改密码
     * @param passwordOld
     * @param passWordNew
     * @param user
     * @return
     */
    ServerResponse<String> resetPassword(String passwordOld,String passWordNew,User user);

    /**
     * 登录状态下更新用户信息
     * @param user
     * @return
     */
    ServerResponse<User> updateUserMsg(User user);

    ServerResponse<User> getUserMsgById(Integer id);
}
