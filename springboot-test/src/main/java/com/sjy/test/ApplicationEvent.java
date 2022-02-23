package com.sjy.test;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;

/**
 * @author xieyan
 * @description
 * @date 2018/12/25.
 */
@Component
@Scope
public class ApplicationEvent implements DisposableBean {

    @PostConstruct
    public void run() {
        System.out.println("sipcloud cdr init over...");
    }

    @PreDestroy
    public void destory1() {

        System.out.println("stop all thread");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("stop all thread DisposableBean");
    }
}
