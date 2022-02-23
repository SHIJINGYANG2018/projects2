package com.sjy.test;

import ch.qos.logback.classic.net.SimpleSocketServer;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
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
        User user = new User("id","2s");
        System.out.println(JSONObject.toJSONString(user));
    }

}
class User{
    @JSONField(name = "user_id")
    private String userId;
    @JSONField(name = "user_id1")
    private String userId1;

    public User() {
    }

    public User(String userId, String userId1) {
        this.userId = userId;
        this.userId1 = userId1;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }
}
