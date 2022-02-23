package com.sjy.redis.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.NonNull;

/**
 * 消息监听器
 */
@Slf4j
public class MyMessageListener implements MessageListener {


    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        try {
            Thread.sleep(1000);
            log.info("message received: {}", message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}