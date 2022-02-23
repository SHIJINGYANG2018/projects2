package com.abc.pay.client.ebus;

import com.abc.pay.client.Base64Code;
import com.abc.pay.client.LogWriter;
import com.abc.pay.client.MerchantConfig;
import com.abc.pay.client.SignService;
import com.abc.pay.client.TrxException;
import com.abc.pay.client.TrxResponse;
import com.abc.pay.client.XMLDocument;

public class PaymentResult extends TrxResponse {
    public PaymentResult(String aMessage) throws TrxException {
        super("Notify", aMessage);
        LogWriter tLogWriter = null;
        SignService sign = null;

        try {
            tLogWriter = new LogWriter();
            tLogWriter.logNewLine("TrustPayClient Java V3.1.7 交易开始==========================");
            tLogWriter.logNewLine("接收到的支付结果通知：\n[" + aMessage + "]");
            String tMessage = Base64Code.Decode64(aMessage);
            tLogWriter.logNewLine("经过Base64解码后的支付结果通知：\n[" + tMessage + "]");
            tLogWriter.logNewLine("验证支付结果通知的签名：");
            sign = SignService.getUniqueInstance();
            XMLDocument tResult = sign.verifySignXML(new XMLDocument(tMessage));
            tLogWriter.logNewLine("验证通过！\n 经过验证的支付结果通知：\n[" + tResult.toString() + "]");
            this.init(tResult);
        } catch (TrxException var14) {
            this.setReturnCode(var14.getCode());
            this.setErrorMessage(var14.getMessage() + "-" + var14.getDetailMessage());
            tLogWriter.log("验证失败！\n");
        } finally {
            if (tLogWriter != null) {
                tLogWriter.logNewLine("交易结束==================================================");

                try {
                    tLogWriter.closeWriter(MerchantConfig.getTrxLogFile());
                } catch (Exception var13) {
                }
            }

        }

    }
}
