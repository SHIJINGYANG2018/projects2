package com.sjy.com.rabbitmq.workqueuemain;

import com.rabbitmq.client.*;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Take2 {

    private static String queueName = "workqueue_sjy";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connetion = RabbitmqConfig.getConnetion();
        Channel channel = connetion.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);

        /*DeliverCallback deliverCallback = (consumerTag, message) -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] 2 Done");
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }

            String msg = new String(message.getBody(), StandardCharsets.UTF_8);

            System.out.println("msg2 = " + msg);
        };
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {

        });*/

        channel.basicConsume(queueName,true,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] 2 Done");
                }
                String msg = new String(body, StandardCharsets.UTF_8);

                System.out.println("msg2 = " + msg);

            }
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
