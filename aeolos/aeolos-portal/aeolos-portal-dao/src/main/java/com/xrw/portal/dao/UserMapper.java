package com.xrw.portal.dao;


import com.xrw.portal.pojo.po.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaorenwu
 */
public interface UserMapper {
    /**查询用户名是否存在
     * @param username
     * @return
     */
    Integer checkUserName(String username);

    /**
     * 根据用户名和用户密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findUserByPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 用户注册
     * @param user
     */
    void addUser(User user);

    /**
     * 验证用email存在
     * @param email
     * @return
     */
    Integer checkEmail(String email);

    /**
     * 通过用户名查询问题
     * @param username
     * @return
     */
    String findQuestionByUserName(@Param("username") String username);

    /**
     * 根据用户名，问题，答案验证答案的正确性
     * @param username
     * @param question
     * @param answer
     * @return
     */
    Integer checkAnswer(@Param("username")String username,@Param("question") String question,@Param("answer") String answer);

    /**
     * 更改用户密码
     * @param username
     * @param passwordNew
     * @return d
     */
    Integer updateUserPassword(@Param("username") String username, @Param("password") String passwordNew);

    /**
     * 通过用户名和密码验证密码正确性
     * @param username
     * @param passwordOld
     * @return
     */
    Integer checkPassword(@Param("username") String username, @Param("password") String passwordOld);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Integer updateUserMsg(User user);

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    User findUserMsgById(Integer id);

}