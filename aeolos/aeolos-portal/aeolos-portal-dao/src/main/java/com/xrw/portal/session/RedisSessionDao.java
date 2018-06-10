package com.xrw.portal.session;

import com.xrw.portal.utils.JedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/10 15:10
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

@Component
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource
    private JedisUtil jedisUtil;

    private final String SHIRO_SESSION_PREFIX = "xrw_session:";

    /**
     * session以二进制的形式进行存储，所以要将要存储的数据进行二进制转换
     */
    private byte[] getKey(String key) {
        return (SHIRO_SESSION_PREFIX + key).getBytes();
    }


    /**
     * 创建session
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    /**
     * 保存session
     */
    private void saveSession(Session session) {
        if (session != null && session.getId() != null) {
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key, value);
            jedisUtil.expire(key, 600);
        }
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read session");
        if (sessionId == null) {
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        byte[] key = getKey(session.getId().toString());
        jedisUtil.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<Session>();
        if (CollectionUtils.isEmpty(keys)) {
            return sessions;
        } else {
            for (byte[] key : keys) {
                Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
                sessions.add(session);
            }
        }
        return sessions;
    }
}
