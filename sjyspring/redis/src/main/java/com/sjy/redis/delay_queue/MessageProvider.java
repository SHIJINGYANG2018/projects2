package com.sjy.redis.delay_queue;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 消息生产者
 *
 * @author shikanatsu
 */
@Component
public class MessageProvider {

    static Logger logger = LoggerFactory.getLogger(MessageProvider.class);

    @Resource
    private RedisMq redisMq;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public boolean sendMessage(@Validated RedisMessage message) {
        Assert.notNull(message);
        //The priority is if there is no creation time
//        message.setCreateTime(System.currentTimeMillis());
        message.setId(IdUtil.fastUUID());
        Long delayTime = message.getCreateTime() + Convert.convertTime(message.getDelay(), TimeUnit.SECONDS, TimeUnit.MILLISECONDS);
        try {
            redisMq.addMsgPool(message);
            redisMq.enMessage(RedisMq.QUEUE_NAME+message.getGroup(), delayTime, message.getId());
            logger.info("RedisMq发送消费信息{}，当前时间:{},消费时间预计{}",message.toString(),new Date(),sdf.format(delayTime));
        }catch (Exception e){
            e.printStackTrace();
            logger.error("RedisMq 消息发送失败，当前时间:{}",new Date());
            return false;
        }
        return true;
    }
}
