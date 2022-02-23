package com.sjy.com.rabbitmq.queuemain;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Take1 {

    private static String QUEUE_NAME = "queue_sjy";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接
        Connection connetion = RabbitmqConfig.getConnetion();
        //通过连接打开一个通道
        Channel channel = connetion.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 当生产者发送数据时调用  监听器
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received -2'" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });


    }

}
