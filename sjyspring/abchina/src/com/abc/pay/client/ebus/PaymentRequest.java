//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc.pay.client.ebus;

import com.abc.pay.client.DataVerifier;
import com.abc.pay.client.JSON;
import com.abc.pay.client.TrxException;
import com.abc.pay.client.TrxRequest;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

public class PaymentRequest extends TrxRequest {
    public LinkedHashMap dicOrder = null;
    public LinkedHashMap orderitems = null;
    public LinkedHashMap dicRequest = null;
    public LinkedHashMap dicSplitAccInfo = null;

    public PaymentRequest() {
        super("EBUS");
        this.dicOrder = new LinkedHashMap();
        this.orderitems = new LinkedHashMap();
        this.dicRequest = new LinkedHashMap();
        this.dicSplitAccInfo = new LinkedHashMap();
        this.dicRequest.put("TrxType", "PayReq");
        this.dicOrder.put("PayTypeID", "");
        this.dicOrder.put("OrderDate", "");
        this.dicOrder.put("OrderTime", "");
        this.dicOrder.put("orderTimeoutDate", "");
        this.dicOrder.put("OrderNo", "");
        this.dicOrder.put("CurrencyCode", "");
        this.dicOrder.put("OrderAmount", "");
        this.dicOrder.put("Fee", "");
        this.dicOrder.put("AccountNo", "");
        this.dicOrder.put("OrderDesc", "");
        this.dicOrder.put("OrderURL", "");
        this.dicOrder.put("ReceiverAddress", "");
        this.dicOrder.put("InstallmentMark", "");
        this.dicOrder.put("InstallmentCode", "");
        this.dicOrder.put("InstallmentNum", "");
        this.dicOrder.put("CommodityType", "");
        this.dicOrder.put("BuyIP", "");
        this.dicOrder.put("ExpiredDate", "");
        this.dicRequest.put("PaymentType", "");
        this.dicRequest.put("PaymentLinkType", "");
        this.dicRequest.put("UnionPayLinkType", "");
        this.dicRequest.put("ReceiveAccount", "");
        this.dicRequest.put("ReceiveAccName", "");
        this.dicRequest.put("NotifyType", "");
        this.dicRequest.put("ResultNotifyURL", "");
        this.dicRequest.put("MerchantRemarks", "");
        this.dicRequest.put("ReceiveMark", "");
        this.dicRequest.put("ReceiveMerchantType", "");
        this.dicRequest.put("IsBreakAccount", "");
        this.dicRequest.put("SplitAccTemplate", "");
    }

    protected void checkRequest() throws TrxException {
        String err = this.isNullCheck();
        if (err.length() != 0) {
            throw new TrxException("1101", "商户提交的交易资料不合法", "订单信息不合法,要素为空！" + err);
        } else {
            String error = this.isOrderValid();
            if (error.length() != 0) {
                throw new TrxException("1101", "商户提交的交易资料不合法", "订单信息不合法！" + error);
            }
        }
    }

    private String isNullCheck() {
        if (this.dicRequest.get("PaymentLinkType") == null) {
            return "未设定支付渠道！";
        } else if (this.dicRequest.get("ResultNotifyURL") == null) {
            return "未设定支付结果回传网址！";
        } else if (this.dicRequest.get("PaymentType") == null) {
            return "未设定支付类型！";
        } else if (this.dicRequest.get("NotifyType") == null) {
            return "未设定支付通知类型！";
        } else if (this.dicOrder.get("PayTypeID") == null) {
            return "未设定交易类型!";
        } else if (this.dicOrder.get("OrderNo") == null) {
            return "未设定交易编号!";
        } else if (this.dicOrder.get("OrderDate") == null) {
            return "未设定订单日期!";
        } else if (this.dicOrder.get("OrderTime") == null) {
            return "未设定订单时间!";
        } else if (this.dicOrder.get("CommodityType") == null) {
            return "未设定商品种类!";
        } else if (this.dicOrder.get("OrderAmount") == null) {
            return "未设定订单金额!";
        } else if (this.dicOrder.get("CurrencyCode") == null) {
            return "未设定交易币种!";
        } else {
            return this.dicRequest.get("IsBreakAccount") == null ? "未设定分账信息!" : "";
        }
    }

    private String isOrderValid() {
        if (this.dicRequest.get("PaymentType").equals("6") && this.dicRequest.get("PaymentLinkType").equals("2")) {
            if (!this.dicRequest.get("UnionPayLinkType").equals("0") && !this.dicRequest.get("UnionPayLinkType").equals("1")) {
                return "银联跨行移动支付接入方式不合法";
            }
        } else {
            this.dicRequest.remove("UnionPayLinkType");
        }

        if (!this.dicRequest.get("NotifyType").equals("0") && !this.dicRequest.get("NotifyType").equals("1")) {
            return "支付通知类型不合法！";
        } else if (!DataVerifier.isValidURL(this.dicRequest.get("ResultNotifyURL").toString())) {
            return "支付结果回传网址不合法！";
        } else if (this.dicRequest.get("ResultNotifyURL").toString().length() == 0) {
            return "支付结果回传网址不合法！";
        } else if (this.dicRequest.get("ResultNotifyURL").toString().getBytes().length > 200) {
            return "支付结果回传网址不合法！";
        } else if (this.dicRequest.get("MerchantRemarks").toString().getBytes().length > 100) {
            return "附言长度大于100";
        } else {
            String payTypeId = this.dicOrder.get("PayTypeID").toString();
            if (!payTypeId.equals("ImmediatePay") && !payTypeId.equals("PreAuthPay") && !payTypeId.equals("DividedPay")) {
                return "设定交易类型错误";
            } else {
                if (payTypeId.equals("DividedPay")) {
                    if (!this.dicOrder.get("InstallmentMark").toString().equals("1")) {
                        return "分期标识为空或输入非法";
                    }

                    if (this.dicOrder.get("InstallmentCode").toString().length() != 8) {
                        return "分期代码长度应该为8位";
                    }

                    if (!DataVerifier.isValidNum(this.dicOrder.get("InstallmentNum").toString(), 2)) {
                        return "分期期数非有效数字或者长度超过2";
                    }
                }

                if (payTypeId.equals("ImmediatePay") || payTypeId.equals("PreAuthPay")) {
                    if (this.dicOrder.get("InstallmentMark").toString().equals("1")) {
                        return "交易类型为直接支付或预授权支付时，分期标识不允许输入为“1”";
                    }

                    this.dicOrder.remove("InstallmentCode");
                    this.dicOrder.remove("InstallmentNum");
                }

                if (this.dicOrder.get("OrderNo").toString().length() == 0) {
                    return "交易编号为空";
                } else if (this.dicOrder.get("OrderNo").toString().getBytes().length > 60) {
                    return "交易编号超长";
                } else if (!DataVerifier.isValidDate(this.dicOrder.get("OrderDate").toString())) {
                    return "订单日期不合法";
                } else if (!DataVerifier.isValidTime(this.dicOrder.get("OrderTime").toString())) {
                    return "订单时间不合法";
                } else if (this.dicOrder.get("CommodityType").toString().length() != 4) {
                    return "商品种类不合法";
                } else if (!this.dicOrder.get("AccountNo").toString().equals("") && this.dicOrder.get("AccountNo").toString().length() < 10) {
                    return "支付账户长度不能少于10位";
                } else {
                    BigDecimal orderAmount = new BigDecimal(this.dicOrder.get("OrderAmount").toString());
                    if (!DataVerifier.isValidAmount(orderAmount, 2)) {
                        return "订单金额不合法";
                    } else if (!this.dicOrder.get("CurrencyCode").toString().equals("156")) {
                        return "设定交易币种错误";
                    } else if (!DataVerifier.isValidString(this.dicRequest.get("IsBreakAccount").toString())) {
                        return "分账信息不合法";
                    } else if (!this.dicRequest.get("IsBreakAccount").toString().equals("0") && !this.dicRequest.get("IsBreakAccount").toString().equals("1")) {
                        return "分账信息不合法";
                    } else if (this.dicRequest.get("IsBreakAccount").toString().equals("0") && this.dicSplitAccInfo.size() > 0) {
                        return "分账标志为0时，不能设置分账信息";
                    } else {
                        new LinkedHashMap();
                        Set<Entry> entries = this.orderitems.entrySet();
                        Iterator iters = entries.iterator();

                        while (iters.hasNext()) {
                            Entry entrys = (Entry) iters.next();
                            LinkedHashMap orderitem = (LinkedHashMap) entrys.getValue();
                            Set<Entry> entrie = orderitem.entrySet();
                            Iterator iter = entrie.iterator();

                            while (iter.hasNext()) {
                                Entry entry = (Entry) iter.next();
                                if (entry.getKey().toString().equals("ProductName") && entry.getValue().toString().length() == 0) {
                                    return "产品名称为空";
                                }
                            }
                        }

                        return "";
                    }
                }
            }
        }
    }

    public String getRequestMessage() throws TrxException {
        String js = "\"Order\":";
        js = js + JSON.WriteDictionary(this.dicOrder);
        if (this.orderitems.size() > 0) {
            js = js + ",\"OrderItems\":";
            js = js + JSON.WriteDictionarys(this.orderitems);
        }

        if (this.dicSplitAccInfo.size() > 0) {
            js = js + ",\"SplitAccInfoItems\":";
            js = js + JSON.WriteDictionarys(this.dicSplitAccInfo);
        }

        js = js + "}";
        String tMessage = JSON.WriteDictionary(this.dicRequest);
        tMessage = tMessage + ",";
        tMessage = tMessage + js;
        return tMessage;
    }
}
