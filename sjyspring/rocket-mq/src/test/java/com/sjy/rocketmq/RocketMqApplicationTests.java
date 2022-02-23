package com.sjy.rocketmq;

import com.sjy.rocketmq.config.JmsConfig;
import com.sjy.rocketmq.producer.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@SpringBootTest
class RocketMqApplicationTests {

    @Autowired
    private Producer producer;

    private List<String> mesList;


    /**
     * 初始化消息
     */

    @Test
    void contextLoads() {
        mesList = new ArrayList<>();
        mesList.add("小小");
        mesList.add("爸爸");
        mesList.add("妈妈");
        mesList.add("爷爷");
        mesList.add("奶奶");
        //总共发送五次消息
        for (String s : mesList) {
            //创建生产信息
            Message message = new Message(JmsConfig.TOPIC, "tag","key", ("小小一家人的称谓:" + s).getBytes());
            //发送
            SendResult sendResult = null;
            try {
                sendResult = producer.getProducer().send(message);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("输出生产者信息={}",sendResult);
        }
    }

}
