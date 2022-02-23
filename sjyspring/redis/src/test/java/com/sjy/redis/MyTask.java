package com.sjy.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

public class MyTask implements Runnable {

    private String lockKey = "lockKey";
    private int taskNum;
    private RedisTemplate<String, Serializable> redisTemplate;

    public MyTask(int taskNum, RedisTemplate<String, Serializable> redisTemplate) {
        this.setTaskNum(taskNum);
        this.setRedisTemplate(redisTemplate);
    }

    public void run() {
        String key = "tes:ssss:1sssdd1";
        RedisLock lock = new RedisLock(redisTemplate, key, 10000, 20000);
        try {
            if (lock.lock()) {
                Thread.sleep(10 * 1000);
                //需要加锁的代码
            } else {
                Thread.sleep(1000);
                System.out.println("鎖住了");
                //this.run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            // 操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            if (lock != null) {
                lock.unlock();
            }
        }

    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}