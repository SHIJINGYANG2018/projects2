package com.sjy.com.rabbitmq.workqueuemain;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sjy.com.rabbitmq.utils.RabbitmqConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static String queueName = "workqueue_sjy";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connetion = RabbitmqConfig.getConnetion();

        Channel channel = connetion.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);


        for (int i = 0; i < 10; i++) {
            String msg = "hello " + i;
            try {
                Thread.sleep(10 + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.basicPublish("", queueName, null, msg.getBytes());
            System.out.println("msg = " + msg);
        }
        channel.close();
        connetion.close();

    }
}
