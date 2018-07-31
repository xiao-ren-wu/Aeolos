package com.xrw.portal.task;

import com.sun.tools.classfile.ConstantPool;
import com.xrw.common.consts.Const;
import com.xrw.common.utils.PropertiesUtil;
import com.xrw.portal.service.OrderService;
import com.xrw.portal.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/7/29 19:42
 * 定时关闭订单
 */
@Component
@Slf4j
public class CloseOrderTask {
    @Resource
    private OrderService orderService;

    @Resource
    private JedisUtil jedisUtil;

    /**
     * 每分钟的整数倍执行一次，不是分布式的环境执行
     */
    //@Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV1(){
        log.info("【关闭订单，定时任务启动】");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        orderService.closeOrder(hour);
        log.info("【关闭订单，定时任务完成】");
    }

    /**
     * 使用shutdown命令关闭tomcat的时候，会在宕机前执行该方法
     * 但是如果tomcat突然宕机，比如说服务器断电，或者直接kill掉tomcat进程，就不会执行其中的任务，
     * 还有就是如果要关闭的锁特别多的话，tomcat会关闭十分缓慢
     */
    @PreDestroy
    public void delLock(){
        jedisUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK.getBytes());
    }

    /**
     * 分布式环境下适用
     *
     * 缺点：
     * 会导致死锁问题，如果获取到锁的tomcat突然宕机，但是还没有执行
     * 对锁设置过期时间的操作，最终导致redis中的设置好的键值对永远不会消失，tomcat2
     * 永远不会获取到锁，导致死锁。
     */
    //@Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV2(){
        log.info("【关闭订单，定时任务启动】");
        Long lockTimeOut = Long.parseLong(PropertiesUtil.getProperty("lock.timeout","2"));
        Long setnxResult = jedisUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeOut));
        if(setnxResult!=null&&setnxResult.intValue()==1){
            //如果返回不为空，并且设置成功，获取到锁，返回值为 1
            closeOrder();
        }else{
            log.info("【没有获取到锁】{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }

        log.info("【关闭订单，定时任务完成】");
    }

    private void closeOrder(){
        jedisUtil.expire(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK.getBytes(),5);
        log.info("获取{},Thread:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time","5000"));
        orderService.closeOrder(hour);
        jedisUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK.getBytes());
        log.info("主动释放{}锁,Thread:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
    }

    /**
     * 多重检验锁--->完美解决
     *
     * 为了防止锁不释放的情况发生，在当前进程没有获得到锁之后，
     * 获取当前锁的value值即锁的过期时间和当前时间作对比，如果当前时间大于锁的过期时间说明锁已经过期
     * 可以说明当前进程已经获取到锁，重置锁的过期时间，使用原子性的getSet方法
     * 该方法如果要更新的键值存在，更新值后，会返回旧值，否则返回null
     * 如果执行了getSet方法后，返回null,可能是为因为其他线程将锁删除掉了，没有关系直接可以使用关闭订单的方法，
     * 如果执行完getSet方法后，返回的值和之前获取到的过期时间的值一样，说明锁设置成功，关闭订单
     * 如果执行完getSet方法后，返回的值和之前的值不一致，说明可能在这期间被其他进程将锁获取到了，不能执行关闭订单的操作
     *
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV3(){
        log.info("关闭定时任务启动");
        long lockTimeOut = Long.parseLong(PropertiesUtil.getProperty("lock.timeout","5000"));
        Long setnxResult = jedisUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,String.valueOf(System.currentTimeMillis()+lockTimeOut));
        if(setnxResult != null && setnxResult.intValue() == 1){
            closeOrder();
        }else{
            //未获取到锁，继续判断，判断时间戳，看是否可以重置并获取到锁
            String lockValueStr = jedisUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK.getBytes()).toString();
            if(lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)){
                String getSetResult = jedisUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,String.valueOf(System.currentTimeMillis()+lockTimeOut));
                //再次用当前时间戳getset。
                //返回给定的key的旧值，->旧值判断，是否可以获取锁
                //当key没有旧值时，即key不存在时，返回nil ->获取锁
                //这里我们set了一个新的value值，获取旧的值。
                if(getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr,getSetResult))){
                    //真正获取到锁
                    closeOrder();
                }else{
                    log.info("没有获取到分布式锁:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            }else{
                log.info("没有获取到分布式锁:{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }

}






























