package com.sjy.redis;

import com.sjy.redis.ip.IPUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;

@SpringBootTest
public class IpTest {


    @Test
    public void getCityInfo() {

        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());
       /* String ip = "124.207.231.242";
        String cityInfo = IPUtil.getCityInfo(ip);
        System.out.println(cityInfo);
        String[] split = cityInfo.split("\\|");

        System.out.println(split[4]);

        System.out.println("split = " + Arrays.toString(split));*/
    }

}
