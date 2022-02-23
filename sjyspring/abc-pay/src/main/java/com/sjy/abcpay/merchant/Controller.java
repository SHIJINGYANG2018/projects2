package com.sjy.abcpay.merchant;

import com.abc.pay.client.JSON;
import com.abc.pay.client.MerchantConfig;
import com.abc.pay.client.ebus.RefundRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjy.abcpay.bean.PayConfig;
import com.sjy.abcpay.service.IPayConfigService;
import com.sjy.abcpay.util.MerchantPayment;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@RestController
public class Controller {

    @Autowired
    IPayConfigService payConfigService;
    /*@Autowired
    MerchantParaFromDB merchantParaFromDB;*/

    @SneakyThrows
    @RequestMapping("MerchantPayment.jsp")
    public void MerchantPayment(HttpServletRequest request) {
        String shopId1 = request.getParameter("shopId");
        Long shopId = shopId1 == "0" ? 0 : Long.parseLong(shopId1);
        QueryWrapper<PayConfig> queryWrapper = new QueryWrapper<PayConfig>()
                .eq("shop_id", shopId == null ? 0 : shopId)
                .eq("pay_type", "abcpay")
                .eq("is_deleted", 0);
        PayConfig payConfig = payConfigService.getOne(queryWrapper);

        Long shopId3 = payConfig.getShopId();
        if (MerchantConfig.getPayConfig() == null) {
            MerchantConfig.setPayConfig(payConfig);
        } else {
            Long shopId2 = MerchantConfig.getPayConfig().getShopId();
            if (!shopId3.equals(shopId2)) {
                MerchantConfig.refreshConfig();
                MerchantConfig.setPayConfig(payConfig);
            }
        }
        MerchantPayment.MerchantPayment(request);
    }

    @Value("${pay.abc.log.path}")
    private String logPath;
    @Value("${pay.abc.error.url}")
    private String errotUrl;

    /* @SneakyThrows
     public void initBase( MerchantParaFromDB merchantParaFromDb){
         String path = new ClassPathResource("/cert/abc.truststore").getURL().getPath();
         String path1 = new ClassPathResource("/cert/TrustPay.cer").getURL().getPath();
         merchantParaFromDb.initBase(logPath,errotUrl,path,path1);
     }*/
    @RequestMapping("MerchantRefund.jsp")
    public void list(HttpServletRequest request) {
        MerchantPayment.MerchantRefund(request);
    }
}
