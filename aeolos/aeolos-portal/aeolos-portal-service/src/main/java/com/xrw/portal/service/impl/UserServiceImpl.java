package com.xrw.portal.service.impl;

import com.xrw.common.cache.TokenCache;
import com.xrw.common.consts.Const;
import com.xrw.portal.dao.UserMapper;
import com.xrw.portal.pojo.po.User;
import com.xrw.portal.pojo.vo.ServerResponse;
import com.xrw.portal.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/9 10:37
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        //1.从数据库中查询是否存在该用户
        Integer num = userMapper.checkUserName(username);
        if (num == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在！！！");
        }
        //2.判断密码是否正确
        //TODO
        User user = userMapper.findUserByPassword(username);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误！！！");
        }
        //3.将密码设置为空
        user.setPassword(null);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse<User> loginByShiro(String username, String password) {
        //1.从数据库中查询是否存在该用户
        Integer num = userMapper.checkUserName(username);
        if (num == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在！！！");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        User user;
        try {
            subject.login(token);
            //从数据库中查询用户信息
            user = userMapper.findUserByPassword(username);
            //将查询到的用户密码设置为空
            user.setPassword(null);
        } catch (AuthenticationException e) {
            String msg = e.getMessage();
            return ServerResponse.createByErrorMessage(msg);
        }

        return ServerResponse.createBySuccess(user);
    }


    @Override
    public ServerResponse<String> register(User user) {
        //到数据库中查询用户名是否存在
        Integer num = userMapper.checkUserName(user.getUsername());
        if (num == 1) {
            return ServerResponse.createByErrorMessage("用户名已存在！！！");
        }
        //验证email是否存在
        Integer checkEmail = userMapper.checkEmail(user.getEmail());
        if (checkEmail == 1) {
            return ServerResponse.createByErrorMessage("email已存在");
        }
        //将新用户的信息添加到数据库中
        //1.设置角色为普通用户
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //2.将传递过来的明文密码使用MD5加密，加盐
        String md5Password = new Md5Hash(user.getPassword(), "salt").toString();
        user.setPassword(md5Password);
        userMapper.addUser(user);
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (type != null) {
            if (Const.USERNAME.equals(type)) {
                Integer checkUserName = userMapper.checkUserName(str);
                if (checkUserName == 1) {
                    return ServerResponse.createBySuccess("校验成功！！！");
                }
                return ServerResponse.createByErrorMessage("用户已存在");
            } else {
                Integer checkEmail = userMapper.checkEmail(str);
                if (checkEmail == 1) {
                    return ServerResponse.createBySuccess("校验成功！！！");
                }
                return ServerResponse.createByErrorMessage("email已存在");
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
    }

    @Override
    public ServerResponse<String> forgetGetQuestion(String username) {
        Integer checkUserName = userMapper.checkUserName(username);
        if (checkUserName == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.findQuestionByUserName(username);
        if (!org.apache.commons.lang3.StringUtils.isNoneBlank(question)) {
            return ServerResponse.createBySuccessMessage("该用户未设置问题");
        }
        return ServerResponse.createBySuccess(question);
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        Integer integer = userMapper.checkAnswer(username, question, answer);
        if (integer > 0) {
            //答案正确
            String forgetToker = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToker);
            return ServerResponse.createBySuccess(forgetToker);
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPasswordToken(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误需要传递token");
        }
        ServerResponse<String> valid = this.checkValid(username, Const.USERNAME);
        if (valid.isSuccess()) {
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String key = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(key) || !StringUtils.equals(forgetToken, key)) {
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        //todo 更新密码
        Integer integer = userMapper.updateUserPassword(username, passwordNew);
        if (integer > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passWordNew, User user) {
        //为了避免横向越权，验证用户的旧密码是否正确
        String username = user.getUsername();
        Integer integer = userMapper.checkPassword(username, passwordOld);
        if (integer == 0) {
            return ServerResponse.createByErrorMessage("密码不正确");
        }
        //更新密码
        //todo md5
        Integer integer1 = userMapper.updateUserPassword(username, passWordNew);
        if (integer1 < 1) {
            return ServerResponse.createByErrorMessage("密码更新失败");
        }
        return ServerResponse.createBySuccess("密码更新成功");
    }

    @Override
    public ServerResponse<User> updateUserMsg(User user) {
        //如果更新email，检查email是否存在，如果别人使用了这个email，则不能更新
        Integer integer = userMapper.checkEmail(user.getEmail());
        if (integer > 0) {
            return ServerResponse.createByErrorMessage("email已被别人使用");
        }
        Integer updateNum = userMapper.updateUserMsg(user);
        if (updateNum > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功", user);
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse<User> getUserMsgById(Integer id) {
        User user = userMapper.findUserMsgById(id);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("未找到该用户");
    }
}
