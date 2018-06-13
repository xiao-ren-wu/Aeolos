//package com.xrw.portal.aop;
//
//import com.xrw.common.consts.Const;
//import com.xrw.portal.pojo.po.User;
//import com.xrw.portal.pojo.vo.ServerResponse;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authz.AuthorizationException;
//import org.apache.shiro.session.Session;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * @CreateBy IDEA
// * @Description: TODO
// * @Author: xiaorenwu
// * @CreateDate: 2018/6/13 0:25
// * @UpdateUser:
// * @UpdateDate:
// * @UpdateRemark: TODO
// * @JdkVersion: jdk1.8.0_101
// */
//
//@Aspect
//@Component
//public class SecurityAspect {
//
//
//    @Pointcut("@annotation(AdminOnly)")
//    public void adminOnly(){
//
//    }
//    @Before("within(com.xrw.portal.service.CategoryService)")
//    public void check() throws Exception{
//        //验证是否登录
//        Session session = SecurityUtils.getSubject().getSession();
//        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        if(user==null){
//            throw new Exception("用户没有登录");
//        }
//        //检查用户权限
//        int admin = Const.Role.ROLE_ADMIN;
//        try {
//            SecurityUtils.getSubject().checkRole(new Integer(admin).toString());
//        } catch (AuthorizationException e) {
//            throw new Exception("该用户没有权限");
//        }
//    }
//}
