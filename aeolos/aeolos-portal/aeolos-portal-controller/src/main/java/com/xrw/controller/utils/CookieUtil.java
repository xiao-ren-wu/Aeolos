package com.xrw.controller.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/7/7 20:15
 *
 *cookie跨域问题
 *
 * X:domain = ".aeolos.com"
 * a:A.aeolos.com           cookie:domian=A.aeolos.com;path="/"
 * b:B.aeolos.com           cookie:domian=A.aeolos.com;path="/"
 * c:A.aeolos.com/test/cc   cookie:domian=A.aeolos.com;path="/test/cc"
 * d:A.aeolos.com/test/dd   cookie:domian=A.aeolos.com;path="/test/dd"
 * e:A.aeolos.com/test      cookie:domian=A.aeolos.com;path="/test"
 *
 * abcde都可以拿到X的cookie
 * 但是B不会拿到其他的cookie，其他也不会拿到B的cookie
 * cd可以访问其他的path，但是cd之间不能相互访问path
 */
@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN=".aeolos.com";
    private final static String COOKIE_NAME="aeolos_login_token";

    /**
     * 服务器向浏览器写入cookie
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        //代表设置成根目录
        cookie.setPath("/");
        //设置cookie的最大存活时间，单位是秒，如果不设置，cookie不会写到硬盘，
        //只会存在内存中，-1代表永久生效，这里设置成一年的有效期
        cookie.setMaxAge(60*60*24*365);
        //无法通过脚本访问cookie
        cookie.setHttpOnly(true);
        log.info("write cookieName{},cookieValue{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    /**
     * 从浏览器中获取cookie
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for (Cookie ck:cookies) {
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(COOKIE_NAME.equals(ck.getName())){
                    log.info("return CookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除指定的cookie,在用户退出登录的时候使用
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie ck:cookies){
                if(COOKIE_NAME.equals(ck.getName())){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    //设置cookie的有效期为0，浏览器接收后会自动删除改cookie
                    ck.setMaxAge(0);
                    log.info("del cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    break;
                }
            }
        }
    }
}
