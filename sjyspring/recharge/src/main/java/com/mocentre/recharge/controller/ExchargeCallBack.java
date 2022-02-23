package com.mocentre.recharge.controller;

import com.mocentre.recharge.entity.CallBack;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("excharge")
public class ExchargeCallBack {

    @PostMapping("/call/back")
    public String callBack(CallBack callBack){
        System.out.println("接收信息"+callBack);
        return "OK";
    }
}
