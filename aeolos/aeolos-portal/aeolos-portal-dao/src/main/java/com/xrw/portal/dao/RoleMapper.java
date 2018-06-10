package com.xrw.portal.dao;

import java.util.List;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/10 9:52
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public interface RoleMapper {
    /**
     * 通过用户名查询用户角色列表
     * @param username
     * @return
     */
    List<String> findRolesByUser(String username);
}
