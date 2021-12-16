package com.sjy.test;

import ch.qos.logback.classic.net.SimpleSocketServer;
import com.sjy.common.utils.config.RedisService;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;


public class TestApplication {
    private static Logger logger = LoggerFactory.getLogger(TestApplication.class);
    public static void main(String[] args) throws Exception {
        System.out.println("start:");
        String[] argss = {"4560", "log4jserver.properties"};
        SimpleSocketServer.main(argss);
        logger.info("succ");
    }

}
