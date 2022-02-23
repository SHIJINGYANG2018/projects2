package com.sjy.redis;

import com.sjy.redis.shishipaiming.AddService;
import com.sjy.redis.shishipaiming.User;
import com.sjy.redis.shishipaiming.ShishiService;
import com.sjy.redis.utils.RedisLock;
import com.sjy.redis.utils.Service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class RedisApplicationTests {
    @Autowired
    private Service service;
    @Resource
    private RedisTemplate<String, User> redisTemplate;
    RedisLock redisLock = null;
    String key = "sjy";

    @Test
    public void contextLoads() {

        service.updateString(key, "1");

        for (int i = 0; i < 100; i++) {

            new Thread(() -> {
                String redis = service.getRedis(key);
                redis += "1";
                service.updateString(key, redis);
            }).start();
            System.out.println(service.getRedis(key));
        }

    }

    @Autowired
    ShishiService shishicontroller;

    /**
     * 实时排名
     */

    @Test
    public void shishipaiming() {
        shishicontroller.init();
        shishicontroller.addSource();
        shishicontroller.top10();
    }

    @Resource
    AddService addService;

    @Test
    public void remove() {
        //删除数组中0~2索引的元素
        shishicontroller.delete();
    }

    /*@Before
    public void init(){
        addService.dd();
    }*/
    @Test
    public void addService() {
        //删除数组中0~2索引的元素
        addService.add();
    }

    @Test
    public void sdd() {
        Boolean delete = redisTemplate.delete("sjy:*");
        assert delete;
    }

}
