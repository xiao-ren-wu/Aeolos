package com.xrw.portal.utils;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class JedisUtil {
    @Resource
    private JedisPool jedisPool;

    private Jedis getResource(){
        return jedisPool.getResource();
    }
    public byte[] set(byte[] key, byte[] value) {
        try (Jedis jedis = getResource()) {
            jedis.set(key, value);
            return value;
        }
    }

    /**
     * 在添加键值对的时候设置他的有效期
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public String setEx(String key,String value,int exTime){
        try (Jedis jedis = getResource()) {
            return jedis.setex(key, exTime, value);
        }
    }

    /**
     * 设置key的有效期
     * @param key
     * @param i 单位是秒
     */
    public void expire(byte[] key, int i) {
        try (Jedis jedis = getResource()) {
            jedis.expire(key, i);
        }
    }

    public byte[] get(byte[] key) {
        try (Jedis jedis = getResource()) {
            return jedis.get(key);
        }
    }

    public void del(byte[] key) {
        try (Jedis jedis = getResource()) {
            jedis.del(key);
        }
    }

    public Set<byte[]> keys(String shiroSessionPrefix) {
        try (Jedis jedis = getResource()) {
            return jedis.keys((shiroSessionPrefix + "*").getBytes());
        }
    }

    /**
     * 分布式锁
     * @param key 分布式锁名称
     * @param value 锁的过期时间
     * @return 获取成功并设置值返回1，否则返回0
     */
    public Long setnx(String key,String value){
        try (Jedis jedis = getResource()) {
            return jedis.setnx(key,value);
        }
    }

    public String getSet(String key,String value){
        try (Jedis jedis = getResource()) {
            return jedis.getSet(key,value);
        }
    }

}
