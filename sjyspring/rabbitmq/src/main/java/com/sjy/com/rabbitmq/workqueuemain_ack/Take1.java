package com.sjy.com.rabbitmq.workqueuemain_ack;

import com.rabbitmq.client.*;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 能者多劳
 */
public class Take1 {

    private static String queueName = "workqueue_sjy_ack";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connetion = RabbitmqConfig.getConnetion();
        Channel channel = connetion.createChannel();
        channel.basicQos(1);
        channel.queueDeclare(queueName, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, message) -> {


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] 1 Done");
                /**
                 * 消息确认
                 * 参数1： 标识要确认消费完成的消息地址
                 * 参数2： 是否批量确认多有信息
                 */
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);

            System.out.println("msg1 = " + msg);


        };
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {

        });


    }

    private static void doWork(String task) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
