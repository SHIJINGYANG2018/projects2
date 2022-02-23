package com.sjy.com.rabbitmq.publish_subscribequeuemain;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * fanout
 * 每个发到 fanout 类型交换器的消息都会分到所有绑定的队列上去。
 * fanout 交换器不处理路由键，只是简单的将队列绑定到交换器上，
 * 每个发送到交换器的消息都会被转发到与该交换器绑定的所有队列上。
 * 很像子网广播，每台子网内的主机都获得了一份复制的消息。fanout 类型转发消息是最快的。
 */
public class Send {
    private static String queueName = "sjy_publish_subscribe_queuen";
    private static String exChangeName = "sjy_publish_subscribe_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = RabbitmqConfig.getConnetion();
        Channel channel = connetion.createChannel();
        // 声明 交换器 以及类型
        channel.exchangeDeclare(exChangeName,"fanout",true);
        /*channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(exChangeName,queueName,"");*/
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(exChangeName,"",null,("publish_subscribe_exchange"+i).getBytes());
        }
        channel.close();
        connetion.close();
    }
}
