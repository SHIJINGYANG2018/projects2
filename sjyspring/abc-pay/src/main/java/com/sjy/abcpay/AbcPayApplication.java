package com.sjy.abcpay;

import com.abc.pay.client.MerchantConfig;
import com.abc.pay.client.TrxException;
import com.sjy.abcpay.merchant.MerchantParaFromDB1;
import com.sjy.abcpay.merchant.ParaClassWeb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@MapperScan("com.sjy.abcpay.mapper")
@SpringBootApplication

@RestController
@RequestMapping("/aaa")
public class AbcPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbcPayApplication.class, args);
    }

    @PostMapping()
    public void postd(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        System.out.println(map);
    }

    @GetMapping
    public void get(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);

    }

    @GetMapping("/{txt}")
    public void get2(@PathVariable("txt") String map, HttpServletRequest request) throws TrxException {
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        System.out.println(map);

        MerchantConfig.refreshConfig();
    }
}
