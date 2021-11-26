package com.sjy.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
//@Scope("prototype")
public class ApplicationRun implements ApplicationRunner, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("ApplicationRun ApplicationRunner");
    }
    @PostConstruct
    public  void run(){
        logger.info("ApplicationRun PostConstruct");
    }

    @Override
    public void destroy() throws Exception {
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("ApplicationRun DisposableBean");
    }
}
