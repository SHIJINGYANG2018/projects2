package com.sjy.com.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 静态路由
 * topic 之恋类型
 */
public class Provide {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = RabbitmqConfig.getConnetion();

        Channel channel = connetion.createChannel();
        String exchangeName ="sjy_topic";
        /**
         * 交换器名称
         * 交换器类型
         */
        channel.exchangeDeclare(exchangeName,"topic");
        String[] routingKeys= {"msg.info","log.msg.error"};

        for (int i = 0; i < 9; i++) {
            channel.basicPublish(exchangeName,routingKeys[i%2],null,("msg = "+i+routingKeys[i%2]).getBytes());
        }
        channel.close();
        connetion.close();
    }
}
