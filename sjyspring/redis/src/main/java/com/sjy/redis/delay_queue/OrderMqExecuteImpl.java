package com.sjy.redis.delay_queue;

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 订单执行
 *
 * @author shikanatsu
 */
@Service
public class OrderMqExecuteImpl implements RedisMqExecute {


    private static Logger logger = LoggerFactory.getLogger(OrderMqExecuteImpl.class);

    public final static String name = "orderPoll:";

    @Resource
    private RedisMqConsumer redisMqConsumer;

    private RedisMqExecute mqExecute = this;


    @Override
    public String getQueueName() {
        return name;
    }

    @Override
    /**
     * For the time being, only all orders will be processed. You can change to make orders
     */
    public boolean execute(RedisMessage message) {
        logger.info("Do orderMqPoll ; Time:{}",new Date());
		//Do 
        return true;
    }

    @Override
    /**  通过线程去执行轮询的过程，时间上可以自由控制 **/
    public void threadPolling() {
        ThreadUtil.execute(() -> {
            while (true) {
                redisMqConsumer.baseMonitor(mqExecute);
                ThreadUtil.sleep(5, TimeUnit.MICROSECONDS);
            }
        });
    }
}
