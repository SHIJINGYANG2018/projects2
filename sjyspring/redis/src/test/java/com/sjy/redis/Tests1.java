package com.sjy.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests1 {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void convertAndSend() {

        for (int i = 0; i < 100; i++) {
            redisTemplate.convertAndSend("sjy", "sjy" + i);
            System.out.println("f发送消息" + i);
        }
    }


}
