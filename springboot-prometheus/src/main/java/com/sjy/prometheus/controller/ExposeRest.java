package com.sjy.prometheus.controller;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1")
public class ExposeRest {

    @GetMapping("/get/{i}")
    public Object testGet(@PathVariable Integer i) throws InterruptedException {
        TimeUnit.SECONDS.sleep(i);
        return "Get SUC" + i +" sleep " +i;
    }
    @GetMapping("/get1/{i}")
    public Object testGet1(@PathVariable Integer i) throws InterruptedException {
        TimeUnit.SECONDS.sleep(i);
        return "Get1 SUC" + i +" sleep " +i;
    }

    @PostMapping("/post/{str}")
    public Object testPost(@PathVariable String str){
        return "Post SUC" + str;
    }

    @PutMapping("/put/{str}")
    public Object testPut(@PathVariable String str){
        return "Put SUC" + str;
    }

    @DeleteMapping("/delete/{str}")
    public Object testDelete(@PathVariable String str){
        return "Delete SUC" + str;
    }
}