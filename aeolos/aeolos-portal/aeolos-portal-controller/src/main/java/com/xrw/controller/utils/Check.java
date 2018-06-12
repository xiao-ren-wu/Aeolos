package com.xrw.controller.utils;

import com.xrw.common.consts.Const;
import com.xrw.portal.pojo.po.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/12 22:45
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public class Check {
    /**
     * 检查当前登录用户是否有管理员权限，有返回true,没有返回false
     * @return 管理员的标志
     */
    public static boolean checkRole(){
        int admin = Const.Role.ROLE_ADMIN;
        try {
            SecurityUtils.getSubject().checkRole(new Integer(admin).toString());
        } catch (AuthorizationException e) {
            return false;
        }
        return true;
    }

    /**
     * 检查用户是否登录,登录返回true,没有登录返回false
     * @return
     */
    public  static boolean checkLogin(){
        //从session中获取当前用户信息，判断该用户是否是管理员身份
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return null != user;
    }
}
