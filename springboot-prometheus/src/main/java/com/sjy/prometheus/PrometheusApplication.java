package com.sjy.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@SpringBootApplication
@RestController
public class PrometheusApplication {
    @Value("${spring.application.name}")
    private  String application;
    public static void main(String[] args) {
        SpringApplication.run(PrometheusApplication.class, args);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName){
        return registry -> registry.config().commonTags("application", applicationName).commonTags("name","shijingyang");
    }
    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws Exception {

        //单文件处理
        //获取上传文件的原始名称
        String originalFilename = file.getOriginalFilename();
        System.out.println("上传文件的大小：" + file.getSize());
        System.out.println("上传文件的类型:" + file.getContentType());
        System.out.println("上传文件时所用的属性名称【key】：" + file.getName());
        File dest = new File(originalFilename);
        file.transferTo(dest);
    }
}
