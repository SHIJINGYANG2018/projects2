package com.sjy.redis;

import com.sjy.redis.utils.RedisBitMapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisBitMap {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisBitMapUtils redisBitMapUtils;

    /**
     * @param userid    用户id
     * @param yearMonth 年月
     * @return
     */
    public String setKey(String userid, String yearMonth) {

        return "user:login:logId:" + userid + ":ym:" + yearMonth;
    }

    @Test
    public void setDates() {

        Random r = new Random();
        int userIdS = r.nextInt(999999);
        long day = (long) r.nextInt(31);
        String dy = "2020" + r.nextInt(12);
        for (int i = 0; i < 1; i++) {
            ValueOperations<String, Object> value = redisTemplate.opsForValue();
            String key = setKey(userIdS + "", dy);
            System.out.println(key);
            value.setBit(key, day, true);
            redisTemplate.expire(key, 31, TimeUnit.DAYS);
        }
       /* ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.setBit("")*/
    }


    @Test
    public void setDate2s() {

        for (int k = 0; k < 8; k++) {

            Random r = new Random();
            int userId = r.nextInt(999999);
            String day = r.nextInt(31) < 10 ? ("0" + r.nextInt(31)) : (r.nextInt(31) + "");
//        String mon = r.nextInt(12)<10?("0"+r.nextInt(12)):(r.nextInt(12)+"");
            for (int i = 0; i < 80; i++) {
                String key = "active:202011" + day;
                redisBitMapUtils.setBit(key, userId, true);
            }
        }
    }
}
