package com.sjy.abcpay.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.math.BigDecimal;
import java.util.List;

public class ExternalOrder {
    /**
     * B2C 导购商户订单新建接口
     *
     * @param openId                用户 ID
     * @param clientId              第三方 ID
     * @param remark                备注
     * @param isInvoice             是否开发票
     * @param totalQty              商品总数目
     * @param totalValue            商品总额
     * @param settlementValue       结算金额
     * @param freightFee            运费
     * @param externalBillNo        导购商户商城 订单编号
     * @param orgId                 导购商户企业 编号
     * @param orderProductModelList 商品明细
     * @param supplierProductCode   导购商户商城 对应的商品编 码
     * @param supplierProductName   导购商户商城 对应的商品名 称
     * @param seqNo                 商品在订单中 的序号
     * @param price                 商品价格
     * @param settlementPrice       商品结算价格
     * @param qty                   购买商品数量
     * @param settlementTotalValue  商品结算金额
     * @param imgNormalUrl          导购商户商城 中 商 品 图 片 URL 地址(可以 通过该地址直 接打开图片)
     */

    public static void orderCreate(String openId, String clientId, String remark,
                                   String isInvoice, String totalQty, BigDecimal totalValue,
                                   String settlementValue, String freightFee, String externalBillNo,
                                   String orgId, List<OrderProductModel> orderProductModelList,
                                   Long supplierProductCode, String supplierProductName,
                                   int seqNo, BigDecimal price, BigDecimal settlementPrice,
                                   BigDecimal qty, BigDecimal settlementTotalValue, String imgNormalUrl) {
        String url = "http://ebiz.test.abchina.com/site-b2c/B2CExternalOrder/OrderCreate";
        JSONObject params = new JSONObject();
        params.put("OpenId", openId);
        params.put("ClientId", clientId);
        params.put("OrgId", openId);
        params.put("IsInvoice", isInvoice);
        params.put("TotalQty", totalValue);
        params.put("TotalValue", totalValue);
        params.put("SettlementValue", settlementValue);
        params.put("FreightFee", freightFee);
        params.put("ExternalBillNo", externalBillNo);
        params.put("OrderProductModelList", orderProductModelList);
        params.put("SupplierProductCode", supplierProductCode);
        params.put("SupplierProductName", supplierProductCode);
        params.put("SeqNo", seqNo);
        params.put("SettlementPrice", settlementPrice);
        params.put("Qty", qty);
        params.put("SettlementTotalValue", settlementTotalValue);
        params.put("ImgNormalUrl", imgNormalUrl);
        HttpRequest request = HttpRequest.post(url)
                .charset("UTF-8")
                .header("Content-Type", "application/json")
                .body(params.toString());
        String body = request.execute().body();
        // String result = HttpUtil.post(url, paramMap);
        System.out.println("result = " + body);
    }

    /**
     * B2C 导购商户订单状态变更接口
     *
     * @param externalBillNo 导购商户商城 中订单唯一编 号
     * @param orgId          导购商户入驻 个人电商平台 时生成的企业 编号
     * @param orderState     枚举值如下： 1-取消订单 2-已完成(已 收货&已评价) 3-已发货(待 收货)
     */
    public void OrderStateChange(String externalBillNo, String orgId, String orderState) {
        String url = "http://ebiz.test.abchina.com/site-b2c/B2CExternalOrder/OrderStateChange";

        JSONObject params = new JSONObject();
        params.put("externalBillNo", externalBillNo);
        params.put("orgId", orgId);
        params.put("orderState", orderState);
        HttpRequest request = HttpRequest.post(url)
                .charset("UTF-8")
                .header("Content-Type", "application/json")
                .body(params.toString());

        String result = request.execute().body();
        if (StrUtil.isNotBlank(result)) {
            JSONObject jsonObject = JSONUtil.parseObj(result);
            if ("00000000 ".equals(jsonObject.get("Code"))) {
                //成功
            }

        }

    }

    class OrderProductModel {

    }
}
