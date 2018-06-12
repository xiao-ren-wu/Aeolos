package com.xrw.portal.realm;

import com.xrw.portal.dao.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;


    /**
     * 用来做授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        //从数据库中获取用户角色
        List<String> roles = roleMapper.findRolesByUser(username);

        return null;
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
