package com.sjy.redis;

import com.sjy.redis.utils.RedisBitMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/bitmap")
public class BitMapRedis {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisBitMapUtils redisBitMapUtils;

    /**
     * @param userid    用户id
     * @param yearMonth 年月
     * @return
     */
    public String setKey(int userid, String year, long Month) {

        return "user:login:logId:" + userid + ":y:" + year + ":m:" + Month;
    }

    @RequestMapping("/setDates")
    @ResponseBody
    public void setDates() {

        Random r = new Random();
        String dy = "2020" + r.nextInt(12);
        for (int i = 0; i < 99; i++) {
            int userIdS = r.nextInt(999999);
            ValueOperations<String, Object> value = redisTemplate.opsForValue();
            long day = (long) r.nextInt(31);
            long mo = (long) r.nextInt(13);
            String key = setKey(userIdS, dy, mo);
            for (long l = 0; l < day; l++) {
                day = (long) r.nextInt(31);
                if (r.nextInt(2) == 1) {
                    value.setBit(key, day, true);
                } else {
                    value.setBit(key, day, false);
                }
            }
            redisTemplate.expire(key, 31, TimeUnit.DAYS);
        }
       /* ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.setBit("")*/
    }


    @RequestMapping("/setDates2")
    @ResponseBody
    public void setDates2() {

        for (int k = 0; k < 8; k++) {

            Random r = new Random();
            int i1 = r.nextInt(31);
            int i2 = r.nextInt(666);
            for (int i = 0; i < i2; i++) {
                int userId = r.nextInt(999999);
                String day = i1 < 10 ? ("0" + i1) : (i1 + "");
                String key = "active:202011" + day;
                redisBitMapUtils.setBit(key, userId, true);
            }
        }
    }

    @RequestMapping("/count")
    @ResponseBody
    public HashMap count() {
        HashMap<String, Long> map = new HashMap<>();
        for (int k = 0; k < 31; k++) {
            String time = (202011 + ((k < 10) ? ("0" + k) : (k + "")));
            String key = "active:" + time;
            Long count = redisBitMapUtils.bitCount(key);
            System.out.println("时间：" + time + " ========>>>>>>>>>>>日活量： " + count);
            map.put(time, count);
        }
        return map;
    }

    @RequestMapping("/bitOp")
    @ResponseBody
    public void bitOp() {
        String[] strings = new String[31];
        for (int k = 0; k < 31; k++) {
            String time = (202011 + ((k < 10) ? ("0" + k) : (k + "")));
            String key = "active:" + time;
            strings[k] = key;
        }
        Long OR = redisBitMapUtils.bitOp(RedisStringCommands.BitOperation.OR, "OR", strings);
        Long AND = redisBitMapUtils.bitOp(RedisStringCommands.BitOperation.AND, "AND", strings);
//        Long NOT = redisBitMapUtils.bitOp(RedisStringCommands.BitOperation.NOT , "NOT",strings);
        Long XOR = redisBitMapUtils.bitOp(RedisStringCommands.BitOperation.XOR, "XOR", strings);
        System.out.println(OR);
        System.out.println(AND);
//        System.out.println(NOT);
        System.out.println(XOR);
    }


}
