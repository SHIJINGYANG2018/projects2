package com.sjy.com.rabbitmq.publish_subscribequeuemain;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 能者多劳
 */
public class Take2 {

    private static String queueName1 = "sjy_publish_subscribe_queuen_1";
    private static String exChangeName = "sjy_publish_subscribe_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connetion = RabbitmqConfig.getConnetion();
        Channel channel = connetion.createChannel();
        channel.exchangeDeclare(exChangeName,"fanout",true);
        /**
         * 持久化的队列
         */
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueBind(queueName1, exChangeName, "");
        DeliverCallback deliverCallback = (consumerTag, message) -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] 2 Done");
            }

            String msg = new String(message.getBody(), StandardCharsets.UTF_8);

            System.out.println("msg2 = " + msg);
        };
        channel.basicConsume(queueName1, true, deliverCallback, consumerTag -> {

        });


    }

    private static void doWork(String task) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
