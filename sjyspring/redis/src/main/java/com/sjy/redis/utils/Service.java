package com.sjy.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Service {

    final
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    public Service(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean updateString(String key, String value) {
        try {
            ValueOperations<String, String> SECONDS = redisTemplate.opsForValue();
            SECONDS.set(key, value);
            redisTemplate.expire(key, 60 * 60, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }
    /*public boolean updateToCache(String key) {
        Long id = groupon.getId();
        if (id == null) {
            return false;
        }
        long time = 12 * 60 * 60;
        String key = RedisKeyConstant.GROUPON_INFO_VALUE + ":" + id;
        return cacheValue(key, groupon, time);
    }

    public String getFromCache(Long shopId) {
        String key = RedisKeyConstant.GROUPON_INFO_VALUE + ":" + shopId;
        String groupon = super.getCacheValue(key);
        if (groupon == null) {
            groupon = super.get(shopId);
            if (groupon != null) {
                this.updateToCache(groupon);
            }
        }
        return groupon;
    }

    public String getFromCache(String key) {

        return super.getCacheValue(key);
    }

    public boolean deleteFromCache(Long shopId) {
        String key = RedisKeyConstant.GROUPON_INFO_VALUE + ":" + shopId;
        return super.removeCache(key);
    }*/

}
