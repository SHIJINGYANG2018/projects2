package com.sjy.com.rabbitmq.queuemain;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static String queueName = "queue_sjy";

    public static void main(String[] args) throws IOException, TimeoutException {

        for (int i = 0; i < 10; i++) {
            sendMessage(i);
        }
    }


    public static void sendMessage(int i) throws IOException, TimeoutException {
        // 连接
        Connection connetion = RabbitmqConfig.getConnetion();
        // 从连接总获取一个通道
        Channel channel = connetion.createChannel();
        //声明队列
        channel.queueDeclare(queueName, false, false, false, null);
        // 发送内容
        String message = "hello sjy" + i;
        // 发布消息数据
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println("con = " + message);
        // 关闭通道
        channel.close();
        // 关闭连接
        connetion.close();
    }
}
