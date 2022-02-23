package com.sjy.test.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationRun3 implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("ApplicationRun3 ApplicationRunner");
    }
    @PostConstruct
    public  void run(){
        logger.info("ApplicationRun3 PostConstruct");
    }
}
