package com.sjy.redis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * redis bitmap （位图） 工具封装
 */

@Component
public class RedisBitMapUtils {

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 不设有效期，同步数据库时，删除key
     *
     * @param key    时间
     * @param offset 偏移量
     * @param value  值
     */
    public boolean setBit(String key, long offset, boolean value) {

        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.setBit(key, offset, value);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, offset [" + offset + "] , value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 获取该偏移量的值
     *
     * @param key
     * @param offset
     * @return
     */
    public boolean getBit(String key, long offset) {

        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            return ops.getBit(key, offset);
        } catch (Throwable e) {
            logger.error("获取缓存失败key[" + key + "], offset [" + offset + "] , error[" + e + "]");
        }
        return false;
    }

    /**
     * 统计
     *
     * @param key
     * @return
     */
    public Long bitCount(final String key) {

        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    public Long bitCount(String key, int start, int end) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }


    /**
     * 运算结果
     *
     * @param op
     * @param saveKey
     * @param desKey
     * @return
     */
    public Long bitOpResult(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        this.bitOp(op, saveKey, desKey);
        return this.bitCount(saveKey);
    }
}

