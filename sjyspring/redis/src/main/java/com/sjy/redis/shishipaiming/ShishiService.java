package com.sjy.redis.shishipaiming;


import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@Component
public class ShishiService {

    ArrayList<User> users1 = new ArrayList<>();
    @Resource
    private RedisTemplate<String, User> redisTemplate;


    /**
     * 排行榜的KEY
     */
    public String KEY_DD = "test:countMoney";
    public String KEY_DD1 = "countMoney1";
    public String KEY_DD2 = "countMoney2";
    public String KEY_DD3 = "countMoney3";

    /**
     * 初始数据
     */

    public void init() {


        ZSetOperations<String, User> zSet = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<User>> users = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            //为用户初始化数据
            User user = new User(i, "sjy" + i, "https://www.baidu.com?" + i);
            redisTemplate.opsForValue().set("sjy:sjy1" + i, user);
            users1.add(user);
            DefaultTypedTuple<User> tuple = new DefaultTypedTuple<User>(user, 0d);
            users.add(tuple);
        }
        // 批量新增
        zSet.add(KEY_DD, users);
    }

    /**
     * 随机人随机加分
     */
    public void addSource() {
        Random random = new Random();
        for (int j = 0; j < 100; j++) {
            int i = random.nextInt(50);
            ZSetOperations<String, User> zSet = redisTemplate.opsForZSet();
            int i1 = new Random().nextInt(6);
            // 加法运算  i1是正增量
            zSet.incrementScore(KEY_DD, users1.get(i), i1);
            //更新 替换
            //zSet.add(KEY_DD,users1.get(i),i1 );
        }
    }

    /**
     * 查询集合中所有的成员
     */

    public Set<User> all() {
        ZSetOperations<String, User> zSet = redisTemplate.opsForZSet();
        return zSet.reverseRange(KEY_DD, 0, -1);
    }

    /**
     * 查看前十名
     */
    public Set<ZSetOperations.TypedTuple<User>> top10() {
        ZSetOperations<String, User> zSet = redisTemplate.opsForZSet();

        // 获取分数前十名的用户
        Set<User> userSet = zSet.reverseRange(KEY_DD, 0, 9);

        assert userSet != null;
        System.out.println(userSet.size());
        for (Serializable serializable : userSet) {
            System.out.print(serializable.toString() + ", ");
        }
        // 获取分数前十名的用户以及对应的分数
        Set<ZSetOperations.TypedTuple<User>> scores = zSet.reverseRangeWithScores(KEY_DD, 0, 10);

        System.out.println();
        assert scores != null;
        System.out.println(scores.size());
        for (ZSetOperations.TypedTuple<User> score : scores) {
            System.out.print(score.getValue() + " : " + (score.getScore().intValue()) + ", ");
        }
        return scores;

    }

    /**
     * 分数之间的用户人数
     */
    public void limitNum1toNum2Count() {

        Long count = redisTemplate.opsForZSet().count(KEY_DD, 0, 10);

    }


    public void intersectAndStore() {

        // KEY_DD1集合与KEY_DD2集合，共同的交集元素存到KEY_DD3（复制），返回元素个数
        redisTemplate.opsForZSet().intersectAndStore(KEY_DD1, KEY_DD2, KEY_DD3);

        // KEY_DD1集合与KEY_DD2集合，共同的并集元素存到KEY_DD3（复制），返回元素个数
        redisTemplate.opsForZSet().unionAndStore(KEY_DD1, KEY_DD2, KEY_DD3);
    }

    /**
     * 删除集合中的元素
     */
    public void remove() {

        //删除集合
        redisTemplate.opsForZSet().remove(KEY_DD, users1.get(0));
        //删除数组中0<=~<=2索引的元素 数据是倒叙的所以删除的是最小的三个值
        //redisTemplate.opsForZSet().removeRange(KEY_DD,0,2);
        // 删除集合中分数是0<=~<=10的元素
        // redisTemplate.opsForZSet().removeRangeByScore(KEY_DD, 0, 9);

    }

    /**
     * 删除集合中的元素
     */
    public void delete() {
        Set<String> keys = redisTemplate.keys("sjy*");
        assert keys != null;
        System.out.println("keys = " + keys);
        Long delete = redisTemplate.delete(keys);
        System.out.println("delete = " + delete);

    }

}
