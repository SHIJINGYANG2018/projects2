package com.sjy.redis.shishipaiming;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class AddService {


    String KEY_DD3 = "sdfsd";
    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    public void dd() {
        redisTemplate.opsForValue().set(KEY_DD3, 0L);
    }

    /**
     * 原子计算
     *
     * @param
     */
    public void add() {
        Long value = redisTemplate.opsForValue().get(KEY_DD3);
        if (value != null) {
            RedisAtomicLong entityIdCounter = new RedisAtomicLong(KEY_DD3, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
            entityIdCounter.expire(3, TimeUnit.SECONDS);//过期时间为3秒
            Long increment = entityIdCounter.getAndIncrement();
            redisTemplate.opsForValue().set(KEY_DD3, increment + value);
        }
    }
}
