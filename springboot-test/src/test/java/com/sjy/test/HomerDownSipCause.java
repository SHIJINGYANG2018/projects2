package com.sjy.test;

import com.sjy.common.utils.OkhttpUtils;

public class HomerDownSipCause {
    public static void main(String[] args) {


        String s = OkhttpUtils.get("https://sip-home-wbsipcloud.vlink.cn/api/cdrModule/cdrSipDownload?cdrCallId=235d672e-b2a9-4302-9f7d-65eed21c6e98&cdrEnterpriseId=5800002&startTime=1628825661");
        System.out.println(s);
    }
}
