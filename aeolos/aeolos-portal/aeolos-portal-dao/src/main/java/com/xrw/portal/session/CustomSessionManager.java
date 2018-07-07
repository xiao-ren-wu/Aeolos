package com.xrw.portal.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/10 15:34
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

@Component
public class CustomSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {

        Serializable sessionId=getSessionId(sessionKey);

        ServletRequest servletRequest=null;
        if(sessionKey instanceof WebSessionKey){
            servletRequest=((WebSessionKey) sessionKey).getServletRequest();
        }
        if(servletRequest!=null&&sessionId !=null){
            Session session= (Session) servletRequest.getAttribute(sessionId.toString());
            if(session!=null){
                return session;
            }
        }
        Session session =super.retrieveSession(sessionKey);
        if(servletRequest!=null&&sessionId!=null){
            servletRequest.setAttribute(sessionId.toString(),session);
        }
        return session;
    }
}

