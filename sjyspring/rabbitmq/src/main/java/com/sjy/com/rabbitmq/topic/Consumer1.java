package com.sjy.com.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = RabbitmqConfig.getConnetion();

        Channel channel = connetion.createChannel();
        String exchangeName ="sjy_topic";
        channel.exchangeDeclare(exchangeName, "topic");
        String queue = channel.queueDeclare().getQueue();

        channel.queueBind(queue,exchangeName,"#.msg.*",null);
        channel.basicConsume(queue,true,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(" [x] Received ----1 '" +
                        envelope.getRoutingKey() + "':'" + msg + "'");
            }
        });
    }
}
