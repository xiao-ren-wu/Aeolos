package com.xrw.portal.task;

import com.xrw.common.utils.PropertiesUtil;
import com.xrw.portal.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    /**
     * 每分钟的整数倍执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV1(){
        log.info("【关闭订单，定时任务启动】");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        orderService.closeOrder(hour);
        log.info("【关闭订单，定时任务完成】");
    }
}
