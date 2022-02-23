package com.sjy.com.rabbitmq.workqueuemain_ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 能者多劳
 */
public class Send {

    private static String queueName = "workqueue_sjy_ack";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = RabbitmqConfig.getConnetion();

        Channel channel = connetion.createChannel();
        channel.confirmSelect();
        // 队列申明
        // durable  true:队列 持久化 false:重启即 删除
        channel.queueDeclare(queueName, true, false, false, null);


        for (int i = 0; i < 10; i++) {
            String msg = "hello " + i;
            try {
                Thread.sleep(10 + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 消息没有持久化
            /**
             * 消息设置持久化
             *             MessageProperties.PERSISTENT_TEXT_PLAIN
             * 设置 deliveryMode属性为2
             */
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            System.out.println("msg = " + msg);
        }
        channel.close();
        connetion.close();

    }
}
