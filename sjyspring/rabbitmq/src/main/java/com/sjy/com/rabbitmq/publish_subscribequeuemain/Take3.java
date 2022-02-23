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
public class Take3 {

    private static String queueName = "sjy_publish_subscribe_queuen";
    private static String exChangeName = "sjy_publish_subscribe_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connetion = RabbitmqConfig.getConnetion();
        Channel channel = connetion.createChannel();
        channel.exchangeDeclare(exChangeName,"fanout",true);
        //临时queue
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, exChangeName, "");
        DeliverCallback deliverCallback = (consumerTag, message) -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] 3Done");
            }

            String msg = new String(message.getBody(), StandardCharsets.UTF_8);

            System.out.println("msg3 = " + msg);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {

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
