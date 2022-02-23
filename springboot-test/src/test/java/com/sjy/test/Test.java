package com.sjy.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sjy.common.utils.OkhttpUtils;
import io.netty.util.NetUtil;
import org.springframework.util.StringUtils;
import sun.plugin2.util.SystemUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: shijingyang
 * @date: 2021/12/13
 */
public class Test {
    private static final Pattern PATTERN_HOSTNAME = Pattern.compile("^.*\\D+([0-9]+)$");

    public static void main(String[] args) throws Exception {
        /*long l = System.currentTimeMillis();
        Set<Long> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 2; i++) {
            long id = IdUtil.nextId(l / 1000);
            String s = Long.toBinaryString(id);
            System.out.println(id);
            System.out.println(s + "_" + s.length());
        }

        Thread.sleep(5000);
        System.out.println(set.size());*/

       long MAX_NEXT = 0b11111111L;

        System.out.println(MAX_NEXT);

        System.out.println(Long.toBinaryString(Long.MAX_VALUE).length());
        System.out.println(Long.toBinaryString(Long.MIN_VALUE).length());
    }


}
