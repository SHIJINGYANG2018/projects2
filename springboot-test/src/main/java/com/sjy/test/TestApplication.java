package com.sjy.test;

import com.sjy.common.utils.config.RedisService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
@Scope
public class TestApplication implements DisposableBean {
    static ConfigurableApplicationContext run;
    public static void main(String[] args) {
        run = SpringApplication.run(TestApplication.class, args);
        System.out.println(22);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(1111);
    }
}
