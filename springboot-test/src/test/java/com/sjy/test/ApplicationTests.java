package com.sjy.test;

import com.sjy.common.utils.config.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() throws InterruptedException {

        redisTemplate.opsForValue().set("key", 1, 1, TimeUnit.SECONDS);

        for (int i = 0; i < 10; i++) {

            System.out.println(i +"  " +redisTemplate.getExpire("key", TimeUnit.MILLISECONDS));
            Thread.sleep(100);
        }
    }

}