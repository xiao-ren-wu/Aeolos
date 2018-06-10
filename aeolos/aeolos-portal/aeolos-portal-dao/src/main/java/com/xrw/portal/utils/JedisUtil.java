package com.xrw.portal.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @CreateBy IDEA
 * @Description: TODO
 * @Author: xiaorenwu
 * @CreateDate: 2018/6/10 15:08
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: TODO
 * @JdkVersion: jdk1.8.0_101
 */

@Component
public class JedisUtil {
    @Resource
    private JedisPool jedisPool;

    private Jedis getResource(){
        return jedisPool.getResource();
    }
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis=getResource();
        try {
            jedis.set(key,value);
            return value;
        } finally {
            jedis.close();
        }
    }

    public void expire(byte[] key, int i) {
        Jedis jedis=getResource();
        try {
            jedis.expire(key,i);
        } finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis=getResource();
        try {
            byte[] value = jedis.get(key);
            return value;
        } finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis=getResource();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        Jedis jedis=getResource();
        try {
            Set<byte[]> keys = jedis.keys((shiro_session_prefix + "*").getBytes());
            return keys;
        } finally {
            jedis.close();
        }
    }

}
