package com.sjy.prometheus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v")
public class VersionRest {

    public static final String VERSION_CODE = "2020.11.10";
    public static final String BUILD_TIME = "2020.11.10 10:14";
    public static final String PROJECT_NAME = "SPRINGBOOT-APP";

    @GetMapping("/1")
    public Object getVersion() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("project", PROJECT_NAME);
        map.put("version", VERSION_CODE);
        map.put("time", BUILD_TIME);
        return map;
    }
}