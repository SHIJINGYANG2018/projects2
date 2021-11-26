package com.sjy.common.utils.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author duyaming
 * @date 2019/1/23 11:51
 */
@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    public static final String LOCK_LUA_SCRIPT = "return redis.call('SET', KEYS[1], ARGV[1], 'NX', 'PX', ARGV[2]) ";

    public static final String LOCK_LUA_SCRIPT_RESULT_OK = "OK";

    public static final String UNLOCK_LUA_SCRIPT = "if (redis.call('GET', KEYS[1]) == ARGV[1]) then "
            + "return redis.call('DEL',KEYS[1]) " + "else " + "return 0 " + "end";

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StringRedisTemplate redisTemplate;

    private BlockingQueue<ConvertAndSend> messageQueue = Queues.newLinkedBlockingQueue(10000);

    private Map<String,String> channelMessageMap = new ConcurrentHashMap<>(10000);

    private Map<String,RateLimiter> channelRate = Maps.newHashMap();

    private String channelMessageMapKey = "%s_%s";

    public Boolean set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    public Boolean setnx(String key, String value) {
        boolean result;
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            logger.error("RedisService.setnx error, key=" + key + " value=" + value, e);
            result = false;
        }

        return result;
    }

    public Boolean setnx(String key, String value, long timeout, TimeUnit unit) {
        boolean result;
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        } catch (Exception e) {
            logger.error("RedisService.setnx error, key=" + key + " value=" + value, e);
            result = false;
        }

        return result;
    }


    public Boolean setex(String key, String value, long timeout, TimeUnit timeUnit) {
        boolean result = true;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception e) {
            logger.error("RedisService.setex error, key=" + key + " value=" + value, e);
            result = false;
        }

        return result;
    }

    public Boolean lock(String key, String value, long expireTime) {
        try {
            DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript();
            defaultRedisScript.setScriptText(LOCK_LUA_SCRIPT);
            defaultRedisScript.setResultType(String.class);
            String execute = redisTemplate.execute(defaultRedisScript, Collections.singletonList(key), value, String.valueOf(expireTime));
            if (LOCK_LUA_SCRIPT_RESULT_OK.equals(execute)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("RedisService.lock error, key=" + key + " value=" + value, e);
            return false;
        }
    }

    public void unlock(String key, String value) {
        try {
            DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript();
            defaultRedisScript.setScriptText(UNLOCK_LUA_SCRIPT);
            defaultRedisScript.setResultType(Long.class);
            redisTemplate.execute(defaultRedisScript, Collections.singletonList(key), value);
        } catch (Exception e) {
            logger.error("RedisService.unlock error, key=" + key + " value=" + value, e);
        }
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key) {
        redisTemplate.delete(key);
        return true;
    }

    public Boolean delete(Set<String> keys) {
        redisTemplate.delete(keys);
        return true;
    }


    public Long inc(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     */
    public Long hIncrBy(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }


    public Set<String> listKeys(String key) {
        return redisTemplate.keys(key);
    }


    public Boolean expire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
        return true;
    }

    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }


    public <T> Boolean set(String key, T t) {
        boolean result = true;
        try {
            String json = mapper.writeValueAsString(t);
            redisTemplate.opsForValue().set(key, json);
        } catch (JsonProcessingException e) {
            logger.error("RedisService.set error, key=" + key + " value=" + t, e);
            result = false;
        }
        return result;
    }

    public <T> Boolean setnx(String key, T t) {
        boolean result;
        try {
            String json = mapper.writeValueAsString(t);
            return redisTemplate.opsForValue().setIfAbsent(key, json);
        } catch (JsonProcessingException e) {
            logger.error("RedisService.setnx error, key=" + key + " value=" + t, e);
            result = false;
        }

        return result;
    }

    public <T> Boolean setex(String key, T t, long timeout, TimeUnit timeUnit) {
        boolean result = true;
        try {
            String json = mapper.writeValueAsString(t);
            redisTemplate.opsForValue().set(key, json, timeout, timeUnit);
        } catch (JsonProcessingException e) {
            logger.error("RedisService.setnx error, key=" + key + " value=" + t, e);
            result = false;
        }

        return result;
    }


    public <T> Boolean multiset(Map<String, T> map) {
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (Map.Entry<String, T> entry : map.entrySet()) {
                    String json;
                    try {
                        json = mapper.writeValueAsString(entry.getValue());
                    } catch (JsonProcessingException e) {
                        continue;
                    }
                    connection.set(((RedisSerializer<String>) redisTemplate.getKeySerializer()).serialize(entry.getKey()),
                            ((RedisSerializer<String>) redisTemplate.getValueSerializer()).serialize(json));
                }
                return null;
            }
        });
        return true;
    }


    public Boolean convertAndSend(String channel, String message) {
        try {
            String key = String.format(channelMessageMapKey,channel,message);
            synchronized (channelMessageMap){
                if(channelMessageMap.get(key) == null){
                    messageQueue.offer(new ConvertAndSend(channel,message));
                    channelMessageMap.put(key,"1");
                    logger.info("xxxxx2"+""+channel+message);

                }
                logger.info("xxxxx1"+""+channel+message);

            }
            logger.info("xxxxx"+""+channel+message);

        } catch (Exception e) {
            logger.error("RedisService.convertAndSend error, channel=" + channel + " message=" + message + " error=" + e);
        }

        return true;
    }

    class ConvertAndSend{
        String channel;
        String message;
        public ConvertAndSend(String channel, String message){
            this.channel = channel;
            this.message = message;
        }
    }

    @PostConstruct
    public void newConvertAndSendThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("ConvertAndSendThread start");
                while (true){
                    try {
                        ConvertAndSend convertAndSend = messageQueue.take();
                        channelMessageMap.remove(String.format(channelMessageMapKey,convertAndSend.channel,convertAndSend.message));
                        RateLimiter limiter = channelRate.get(convertAndSend.channel);
                        if(limiter == null){
                            limiter = RateLimiter.create(1);
                            channelRate.put(convertAndSend.channel,limiter);
                        }
                        if(limiter.tryAcquire()){
                            redisTemplate.convertAndSend(convertAndSend.channel, convertAndSend.message);
                            logger.info(convertAndSend.channel+"   "+ convertAndSend.message);
                        }else {
                            convertAndSend(convertAndSend.channel, convertAndSend.message);
                        }
                    } catch (InterruptedException e) {
                        logger.error("ConvertAndSendThread convertAndSend error:",e);
                    }
                }
            }
        },"ConvertAndSendThread").start();
    }


    public String srandmember(String key) {
        try {
            return redisTemplate.opsForSet().randomMember(key);
        } catch (Exception e) {
            logger.error("redisService srandmember e:{}", e);
        }
        return null;
    }


    public boolean sadd(String key, String... value) {
        try {

            redisTemplate.opsForSet().add(key, value);
            return true;

        } catch (Exception e) {
            logger.error("redisService sadd e:{}", e);
        }
        return false;
    }

    public boolean isSetMember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);

        } catch (Exception e) {
            logger.error("redisService isSetMember e:{}", e);
        }
        return false;
    }


    public boolean sdelete(String key, String... value) {
        try {

            redisTemplate.opsForSet().remove(key, value);
            return true;

        } catch (Exception e) {
            logger.error("redisService sdelete e:{}", e);
        }
        return false;
    }

    public Set<String> smembers(String key) {
        Set<String> result = null;
        try {
            result = redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error("redisService smembers e:{}", e);
        }
        return result;
    }

    public Long ttl(String key, TimeUnit timeUnit) {
        long ttl = 0;
        try {
            ttl = redisTemplate.getExpire(key, timeUnit);
        } catch (Exception e) {
            logger.error("redisService ttl e:{}", e);
        }
        return ttl;
    }


    /**
     * PING
     */
    public String ping() {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }


    public Set<String> scan(String pattern) {

        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).build();
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                boolean done = false;
                Set<String> keySet = new HashSet<String>();
                while (!done) {
                    Cursor<byte[]> c = connection.scan(scanOptions);
                    try {
                        while (c.hasNext()) {
                            byte[] b = c.next();
                            keySet.add(new String(b));
                        }
                        done = true;
                    } catch (NoSuchElementException nse) {
                        logger.error("RedisService.scan error", nse);
                    }
                }
                return keySet;
            }
        });
    }

    public Map<String, String> hscan(String key, String pattern) {
        try {
            ScanOptions.ScanOptionsBuilder scanOptionsBuilder = ScanOptions.scanOptions();
            if (pattern != null && pattern.length() > 0) {
                scanOptionsBuilder.match(pattern);
            }
            ScanOptions scanOptions = scanOptionsBuilder.build();
            return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
                @Override
                public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                    boolean done = false;
                    Map<String, String> map = Maps.newHashMap();
                    while (!done) {
                        Cursor<Map.Entry<byte[], byte[]>> c = connection.hScan(key.getBytes(), scanOptions);
                        try {
                            while (c.hasNext()) {
                                Map.Entry<byte[], byte[]> b = c.next();
                                map.put(new String(b.getKey()), new String(b.getValue()));
                            }
                            done = true;
                        } catch (NoSuchElementException nse) {
                            logger.error("RedisService.hscan error:", nse);
                        }
                    }
                    return map;
                }
            });
        } catch (Exception e) {
            logger.error("RedisService hscan e:{}", e);
            return null;
        }
    }

    public String type(String key) {
        String type = null;
        try {
            type = redisTemplate.type(key).code();
            logger.info("key:{} type:{}", key, type);
        } catch (Exception e) {
            logger.error("type e:{}", e);
        }
        return type;
    }


    public Set<String> members(String key) {
        Set<String> set = null;
        try {
            set = redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error("members e:{}", e);
        }
        return set;
    }

    public boolean rPush(String key, String value) {
        try {
            Long aLong = redisTemplate.opsForList().rightPush(key, value);
            if (null != aLong && aLong > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("rPush e:{}", e);
        }
        return false;
    }

    public String rPop(String key, long timeout, TimeUnit unit ) {
        try {
            return  redisTemplate.opsForList().rightPop(key, timeout,  unit);
        } catch (Exception e) {
            logger.error("rPop e:{}", e);
        }
        return null;
    }

    public List<String> lRange(String key, long start, long end) {
        List<String> list = null;
        try {
            list = redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error("lRange e:{}", e);
        }
        return list;
    }

    public boolean hset(String key, Object hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
        } catch (Exception e) {
            logger.error("hadd e:", e);
            return false;
        }
        return true;
    }

    public Object hget(String key, String hashKey) {
        try {
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            logger.error("hget e:", e);
            return null;
        }
    }

    public Map<Object, Object> hgetAll(String key) {
        Map<Object, Object> map = Maps.newHashMap();
        try {
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
            if (entries != null && entries.size() > 0) {
                map.putAll(entries);
            }
        } catch (Exception e) {
            logger.error("hadd e:", e);
        }
        return map;
    }

    public Long hdel(String key, Object... hashKey) {
        try {
            return redisTemplate.opsForHash().delete(key, hashKey);
        } catch (Exception e) {
            logger.error("hdel e:", e);
            return null;
        }
    }


    /**
     * HLEN key
     * Get the number of fields in a hash
     * @param key
     * @return
     */
    public Long hlen(String key) {
        return redisTemplate.opsForHash().size(key);
    }



}
