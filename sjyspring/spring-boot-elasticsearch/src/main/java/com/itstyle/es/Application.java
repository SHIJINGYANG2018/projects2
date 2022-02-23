package com.itstyle.es;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动类
 * 创建者  小柒2012
 * 网址 https://blog.52itstyle.vip
 * 创建时间	2018年1月22日
 * linux 下 后台启动  nohup java -jar spring-boot2-elasticsearch.jar --server.port=8080 &
 * <p>
 * 5.4文档 ：http://cwiki.apachecn.org/pages/viewpage.action?pageId=4260364
 * <p>
 * 5.6.9下载地址：https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.6.9.zip
 * <p>
 * kafka :https://www.apache.org/dyn/closer.cgi?path=/kafka/2.3.0/kafka_2.11-2.3.0.tgz
 */
@SpringBootApplication
//@EnableDubboConfiguration
public class Application extends SpringBootServletInitializer {
    private static final Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Application.class, args);
        logger.info("全文搜索服务启动");
    }
}