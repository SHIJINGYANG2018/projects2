package com.sjy.test.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class ApplicationRun4 implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("ApplicationRun4 InitializingBean");
    }
    @PostConstruct
    public  void run(){
        logger.info("ApplicationRun4 PostConstruct");
    }
}
