
package com.sjy.redis.queue;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列
 * 队列的操作  push数据 pull 数据
 */
@Logger
public class SaveorderQueue<T> implements InitializingBean, DisposableBean {

    private RedisTemplate redisTemplate;
    private int cap = Short.MAX_VALUE; // 最大阻塞的容量，超过容量将会导致清空旧数据
    private byte[] rawKey;
    private RedisConnectionFactory factory;
    private RedisConnection connection;
    private BoundListOperations<String, T> listOperations;
    private String key;
    private Lock lock = new ReentrantLock(); // 基于底层IO阻塞考虑
    private ThreadPoolManage threadPool;
    private Thread listenerThread;
    private boolean working = false;
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {

        factory = redisTemplate.getConnectionFactory();
        connection = RedisConnectionUtils.getConnection(factory);
        rawKey = redisTemplate.getKeySerializer().serialize(key);
        listOperations = redisTemplate.boundListOps(key);
        threadPool = new ThreadPoolManage();
        listenerThread = new ListenerThread();
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    @SuppressWarnings("unchecked")
    public T takeFromTail(int timeout) throws InterruptedException {

        lock.lockInterruptibly();
        try {
            List<byte[]> results = connection.bRPop(timeout, rawKey);
            if (CollectionUtils.isEmpty(results)) {
                return null;
            }
            return (T) redisTemplate.getValueSerializer().deserialize(results.get(1));
        } finally {
            lock.unlock();
        }
    }

    public T takeFromTail() throws InterruptedException {

        return takeFromTail(0);
    }

    @SuppressWarnings("unchecked")
    public T takeFromHead(int timeout) throws InterruptedException {

        lock.lockInterruptibly();
        try {
            List<byte[]> results = connection.bLPop(timeout, rawKey);
            if (CollectionUtils.isEmpty(results)) {
                return null;
            }
            return (T) redisTemplate.getValueSerializer().deserialize(results.get(1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public T takeFromHead() throws InterruptedException {

        return takeFromHead(0);
    }

    public void pushFromHead(T value) {

        listOperations.leftPush(value);
    }

    public void pushFromTail(T value) {

        listOperations.rightPush(value);
    }

    public T removeFromHead() {

        return listOperations.leftPop();
    }

    public T removeFromTail() {

        return listOperations.rightPop();
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    public void setKey(String key) {

        this.key = key;
    }

    class ListenerThread extends Thread {

        @Override
        public void run() {

            try {
                while (true) {
                    if (!working) {
                        if (applicationContext != null) {
                            working = true;
                        }
                        sleep(1000);
                    } else {
                        T value = takeFromHead();
                        if (value != null) {
                            MsgVo msg = (MsgVo) value;
                            threadPool.process(msg);
                        } else {
                            sleep(1000);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() throws Exception {

        shutdown();
        RedisConnectionUtils.releaseConnection(connection, factory);
    }

    private void shutdown() {

        try {
            listenerThread.interrupt();
            threadPool.destory();
        } catch (Exception e) {
        }
    }

}
