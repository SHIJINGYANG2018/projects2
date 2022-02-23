package com.sjy.upload;

import com.alibaba.fastjson.JSON;
import com.sjy.common.utils.OkhttpUtils;

import java.util.HashMap;

public class Test {
    public static void main(String[] args) {

        HashMap<String, String> map = new HashMap<>();

        map.put("appId","LHOyxLXK");
        map.put("appKey","8h6VuWhW");
        map.put("mobiles","18363997970");
        map.put("type","0");
        String post = OkhttpUtils.post("https://api.253.com/open/unn/batch-ucheck", map);


    }
}
