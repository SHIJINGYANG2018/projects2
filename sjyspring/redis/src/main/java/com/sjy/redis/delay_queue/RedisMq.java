package com.sjy.redis.delay_queue;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisMq {

    /**
     * 消息池前缀，以此前缀加上传递的消息id作为key，以消息{@link MSG_POOL}
     * 的消息体body作为值存储
     */
    public static final String MSG_POOL = "Message:Pool:";

    /**
     * zset队列 名称 queue
     */
    public static final String QUEUE_NAME = "Message:Queue:";

//    private static final int SEMIH = 30 * 60;


    @Resource
    private RedisTemplate redisService;

    /**
     * 存入消息池
     *
     * @param message
     * @return
     */
    public boolean addMsgPool(RedisMessage message) {
        if (null != message) {
            ValueOperations valueOperations = redisService.opsForValue();
            valueOperations.set(MSG_POOL + message.getGroup() + message.getId(), message, message.getTtl());
            return true;
        }
        return false;
    }

    /**
     * 从消息池中删除消息
     *
     * @param id
     * @return
     */
    public void deMsgPool(String group, String id) {
        redisService.delete(MSG_POOL + group + id);
    }

    /**
     * 向队列中添加消息
     *
     * @param key
     * @param score 优先级
     * @param val
     * @return 返回消息id
     */
    public void enMessage(String key, long score, String val) {
        ZSetOperations zSetOperations = redisService.opsForZSet();
        zSetOperations.add(key, val, score);
    }

    /**
     * 从队列删除消息
     *
     * @param id
     * @return
     */
    public void deMessage(String key, String id) {
        ZSetOperations zSetOperations = redisService.opsForZSet();
        zSetOperations.remove(key, id);
    }

}
