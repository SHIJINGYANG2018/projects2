package com.sjy.com.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqConfig {

    public static Connection getConnetion() throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("172.31.67.105");
        cf.setPort(5672);
        cf.setUsername("test");
        cf.setPassword("test");
        cf.setVirtualHost("/test");
        return cf.newConnection();
    }

}
