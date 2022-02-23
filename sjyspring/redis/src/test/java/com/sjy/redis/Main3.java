package com.sjy.redis;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.json.JSONArray;

import javax.sound.midi.Soundbank;
import java.util.Random;

public class Main3 {
    public static void main(String[] args) {

       /* String txt = "{\"aliyunDomain\": \"xxxxxxxxx\", \t  \"aliyunAccessKeyId\":\"yyyyyyyyyyyyy\", \t\t\"aliyunAccessKeySecret\":\"mmmmmmmmmmmmm\" }";

        CloudStorageConfig cloudStorageConfig = JSONUtil.toBean(new JSONObject(txt), CloudStorageConfig.class);
        System.out.println(cloudStorageConfig);*/

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int s = random.nextInt(2);
            System.out.println(s);

        }
    }
}
