package com.abc.pay.client.ebus;

import com.abc.pay.client.DataVerifier;
import com.abc.pay.client.JSON;
import com.abc.pay.client.TrxException;
import com.abc.pay.client.TrxRequest;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * 退款请求对象
 */
public class RefundRequest extends TrxRequest {
    public LinkedHashMap<String, String> dicRequest = null;
    public LinkedHashMap dicSplitAccInfo = null;

    public RefundRequest() {
        super("EBUS");
        this.dicRequest = new LinkedHashMap();
        this.dicSplitAccInfo = new LinkedHashMap();
        this.dicRequest.put("TrxType", "Refund");
        this.dicRequest.put("OrderDate", "");
        this.dicRequest.put("OrderTime", "");
        this.dicRequest.put("MerRefundAccountNo", "");
        this.dicRequest.put("MerRefundAccountName", "");
        this.dicRequest.put("OrderNo", "");
        this.dicRequest.put("NewOrderNo", "");
        this.dicRequest.put("CurrencyCode", "");
        this.dicRequest.put("TrxAmount", "");
        this.dicRequest.put("RefundType", "");
        this.dicRequest.put("MerchantRemarks", "");
        this.dicRequest.put("MerRefundAccountFlag", "");
    }

    @Override
    protected void checkRequest() throws TrxException {
        if (this.dicRequest.get("OrderNo") != null && ((String) this.dicRequest.get("OrderNo")).toString().length() <= 60) {
            if (this.dicRequest.get("NewOrderNo") != null && ((String) this.dicRequest.get("NewOrderNo")).toString().length() <= 60) {
                if (this.dicRequest.get("OrderDate") != null && DataVerifier.isValidDate(((String) this.dicRequest.get("OrderDate")).toString())) {
                    if (this.dicRequest.get("OrderTime") != null && DataVerifier.isValidTime(((String) this.dicRequest.get("OrderTime")).toString())) {
                        if (this.dicRequest.get("CurrencyCode") != null && ((String) this.dicRequest.get("CurrencyCode")).toString().equals("156")) {
                            if (this.dicRequest.get("TrxAmount") == null) {
                                throw new TrxException("1101", "商户提交的交易资料不合法", "交易金额未设定！");
                            } else {
                                String amountStr = ((String) this.dicRequest.get("TrxAmount")).toString();
                                BigDecimal amount = new BigDecimal(amountStr);
                                if (!DataVerifier.isValidAmount(amount, 2)) {
                                    throw new TrxException("1101", "商户提交的交易资料不合法", "交易金额不合法！");
                                }
                            }
                        } else {
                            throw new TrxException("1100", "商户提交的交易资料不完整", "设定交易币种不合法！");
                        }
                    } else {
                        throw new TrxException("1100", "商户提交的交易资料不完整", "订单时间不合法！");
                    }
                } else {
                    throw new TrxException("1100", "商户提交的交易资料不完整", "订单日期不合法！");
                }
            } else {
                throw new TrxException("1100", "商户提交的交易资料不完整", "交易编号不合法！");
            }
        } else {
            throw new TrxException("1100", "商户提交的交易资料不完整", "原交易编号不合法！");
        }
    }

    @Override
    public String getRequestMessage() throws TrxException {
        String tMessage = JSON.WriteDictionary(this.dicRequest);
        String js = "";
        if (this.dicSplitAccInfo.size() > 0) {
            js = js + "\"SplitMerInfo\":";
            js = js + JSON.WriteDictionarys(this.dicSplitAccInfo);
            tMessage = tMessage + ",";
            tMessage = tMessage + js;
        }

        return tMessage;
    }

    public void setConnectionFlag(boolean flag) {
        super.connectionFlag = flag;
    }
}
