package com.xrw.common.enums;

import org.apache.shiro.util.StringUtils;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/13 17:54
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

public enum CacheCode {
    TOKEN_FREFIX(404,"TOKEN_FREFIX");
    private final Integer code;
    private final String msg;

    CacheCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
