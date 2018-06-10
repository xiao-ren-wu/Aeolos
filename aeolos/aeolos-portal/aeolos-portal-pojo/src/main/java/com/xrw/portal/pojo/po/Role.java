package com.xrw.portal.pojo.po;

import java.util.Date;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/10 9:53
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public class Role {
    private Integer id;

    private String username;

    private String role;

    private Date createTime;

    private Date updateTime;

    public Role(Integer id, String username, String role, Date createTime, Date updateTime) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
