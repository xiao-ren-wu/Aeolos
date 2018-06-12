package com.xrw.portal.realm;

import com.xrw.common.consts.Const;
import com.xrw.portal.dao.UserMapper;
import com.xrw.portal.pojo.po.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @CreateBy IDEA
 * @Description: 自定义Realm
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/10 9:38
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserMapper userMapper;

    /**
     * 用来做授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //从session中获取用户角色
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        Integer role = user.getRole();
        //将角色数据返回
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        set.add(role.toString());
        simpleAuthorizationInfo.setRoles(set);
        return simpleAuthorizationInfo;
    }






    /**
     * 用来做认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username=(String)token.getPrincipal();
        String password = userMapper.findPasswordByUsername(username);
        if(null!=password){
            //创建返回对象
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(username,password,"hello Shiro");
            //加盐
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
