package com.sjy.common;


import com.sjy.common.utils.OkhttpUtils;

import java.util.ArrayList;

class CommonApplicationTests {

    void contextLoads() {
    }


    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            int finalI = i;
            new Thread(()->{
                OkhttpUtils.get("http://127.0.0.1:8201/v1/get/sjy"+(finalI /5));
                OkhttpUtils.get("http://127.0.0.1:8201/v/1");
            }).start();
        }
    }
}
