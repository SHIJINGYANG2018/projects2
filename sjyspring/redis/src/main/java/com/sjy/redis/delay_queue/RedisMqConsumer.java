package com.sjy.redis.delay_queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Redis消息消费者
 * @author shikanatsu
 */
@Component
public class RedisMqConsumer {

    private static final Logger log = LoggerFactory.getLogger(RedisMqConsumer.class);

    @Resource
    private RedisMq redisMq;

    @Resource
    private RedisTemplate redisService;

    @Resource
    private MessageProvider provider;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //@Scheduled(cron = "*/1 * * * * ? ")
    /**
     Instead of a thread loop, you can use Cron expressions to perform periodic tasks
     */
    public void baseMonitor(RedisMqExecute mqExecute){
        String queueName = RedisMq.QUEUE_NAME+mqExecute.getQueueName();
        //The query is currently expired

        Set<Object> set =  redisService.opsForZSet().rangeByScore(queueName, 0, System.currentTimeMillis());
        if (null != set) {
            long current = System.currentTimeMillis();
            for (Object id : set) {
                long  score = redisService.opsForZSet().score(queueName, id.toString()).longValue();
                //Once again the guarantee has expired ， And then perform the consumption
                if (current >= score) {
                    String str = "";
                    RedisMessage message = null;
                    String msgPool = RedisMq.MSG_POOL+mqExecute.getQueueName();
                    try {
                        message = (RedisMessage)redisService.opsForValue().get(msgPool + id.toString());
                        log.debug("RedisMq:{},get RedisMessage success now Time:{}",str,sdf.format(System.currentTimeMillis()));
                        if(null==message){
                            return;
                        }
                        //Do something ； You can add a judgment here and if it fails you can add it to the queue again
                        mqExecute.execute(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //If an exception occurs, it is put back into the queue
                        // todo：  If repeated, this can lead to repeated cycles
                        log.error("RedisMq: RedisMqMessage exception ,It message rollback , If repeated, this can lead to repeated cycles{}",new Date());
                        provider.sendMessage(message);
                    } finally {
                        redisMq.deMessage(queueName, id.toString());
                        redisMq.deMsgPool(message.getGroup(),id.toString());
                    }
                }
            }
        }
    }
}
