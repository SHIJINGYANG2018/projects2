package com.sjy.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class ApplicationRun2 implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("ApplicationRun2 InitializingBean");
    }
    @PostConstruct
    public  void run(){
        logger.info("ApplicationRun2 PostConstruct");
    }
}
