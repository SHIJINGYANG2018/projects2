package com.sjy.redis.delay_queue;

public interface RedisMqExecute {

    /**
     * 获取队列名称
     * @return
     */
    public String getQueueName();


    /**
     * 统一的通过执行期执行
     * @param message
     * @return
     */
    public boolean execute(RedisMessage message);


    /**
     * Perform thread polling
     */

    public void   threadPolling();

}

