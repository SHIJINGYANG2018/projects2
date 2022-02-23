package com.sjy.redis;

import com.sjy.redis.utils.MyMessageListener;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@SpringBootApplication
public class RedisApplication {

    @Autowired
    RedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);

    }

    /**
     * RedisMessageListenerContainer提供订阅消息的多路分发，这样多个订阅可以共享同一个Redis连接.
     */
    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(new MyMessageListener(), new ChannelTopic("sjy"));
        return container;
    }


}
