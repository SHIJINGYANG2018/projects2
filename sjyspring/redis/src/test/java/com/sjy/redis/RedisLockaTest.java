package com.sjy.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockaTest {

    ArrayList<Integer> integers = new ArrayList<>();
    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void sss() {
        // 定义线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        // 添加10个线程获取锁
        for (int i = 1; i < 10; i++) {
            MyTask myTask = new MyTask(i, redisTemplate);
            pool.execute(myTask);
            System.out.println("线程池中线程数目：" + pool.getPoolSize() + "，队列中等待执行的任务数目：" +
                    pool.getQueue().size() + "，已执行玩别的任务数目：" + pool.getCompletedTaskCount());
        }

        pool.shutdown();
    }
}

