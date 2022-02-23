package com.sjy.abcpay.util;

import com.abc.pay.client.*;
import com.abc.pay.client.ebus.*;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * 支付、退款请求
 */
@UtilityClass
public class MerchantPayment {


    //支付请求
    @SneakyThrows
    public void MerchantPayment(HttpServletRequest request) {

        //1、生成订单对象
        PaymentRequest tPaymentRequest = new PaymentRequest();
        tPaymentRequest.dicOrder.put("PayTypeID", request.getParameter("PayTypeID"));                   //设定交易类型
        tPaymentRequest.dicOrder.put("OrderDate", request.getParameter("OrderDate"));                  //设定订单日期 （必要信息 - YYYY/MM/DD）
        tPaymentRequest.dicOrder.put("OrderTime", request.getParameter("OrderTime"));                   //设定订单时间 （必要信息 - HH:MM:SS）
        tPaymentRequest.dicOrder.put("orderTimeoutDate", request.getParameter("orderTimeoutDate"));     //设定订单有效期
        tPaymentRequest.dicOrder.put("OrderNo", request.getParameter("OrderNo"));                       //设定订单编号 （必要信息）
        tPaymentRequest.dicOrder.put("CurrencyCode", request.getParameter("CurrencyCode"));             //设定交易币种
        tPaymentRequest.dicOrder.put("OrderAmount", request.getParameter("PaymentRequestAmount"));      //设定交易金额
        tPaymentRequest.dicOrder.put("Fee", request.getParameter("Fee"));                               //设定手续费金额
        tPaymentRequest.dicOrder.put("AccountNo", request.getParameter("AccountNo"));                   //设定支付账户
        tPaymentRequest.dicOrder.put("OrderDesc", request.getParameter("OrderDesc"));                   //设定订单说明
        tPaymentRequest.dicOrder.put("OrderURL", request.getParameter("OrderURL"));                     //设定订单地址
        tPaymentRequest.dicOrder.put("ReceiverAddress", request.getParameter("ReceiverAddress"));       //收货地址
        tPaymentRequest.dicOrder.put("InstallmentMark", request.getParameter("InstallmentMark"));       //分期标识
        if (request.getParameter("InstallmentMark") == "1" && request.getParameter("PayTypeID") == "DividedPay") {
            tPaymentRequest.dicOrder.put("InstallmentCode", request.getParameter("InstallmentCode"));   //设定分期代码
            tPaymentRequest.dicOrder.put("InstallmentNum", request.getParameter("InstallmentNum"));     //设定分期期数
        }
        tPaymentRequest.dicOrder.put("CommodityType", request.getParameter("CommodityType"));           //设置商品种类
        tPaymentRequest.dicOrder.put("BuyIP", request.getParameter("BuyIP"));                           //IP
        tPaymentRequest.dicOrder.put("ExpiredDate", request.getParameter("ExpiredDate"));               //设定订单保存时间

        //2、订单明细
        LinkedHashMap orderitem = new LinkedHashMap();
        orderitem.put("SubMerName", "测试二级商户1");    //设定二级商户名称
        orderitem.put("SubMerId", "12345");    //设定二级商户代码
        orderitem.put("SubMerMCC", "0000");   //设定二级商户MCC码
        orderitem.put("SubMerchantRemarks", "测试");   //二级商户备注项
        orderitem.put("ProductID", "IP000001");//商品代码，预留字段
        orderitem.put("ProductName", "中国移动IP卡");//商品名称
        orderitem.put("UnitPrice", "1.00");//商品总价
        orderitem.put("Qty", "1");//商品数量
        orderitem.put("ProductRemarks", "测试商品"); //商品备注项
        orderitem.put("ProductType", "充值类");//商品类型
        orderitem.put("ProductDiscount", "0.9");//商品折扣
        orderitem.put("ProductExpiredDate", "10");//商品有效期
        tPaymentRequest.orderitems.put(1, orderitem);

        orderitem = new LinkedHashMap();
        orderitem.put("SubMerName", "测试二级商户1");    //设定二级商户名称
        orderitem.put("SubMerId", "12345");    //设定二级商户代码
        orderitem.put("SubMerMCC", "0000");   //设定二级商户MCC码
        orderitem.put("SubMerchantRemarks", "测试");   //二级商户备注项
        orderitem.put("ProductID", "IP000001");//商品代码，预留字段
        orderitem.put("ProductName", "中国联通IP卡");//商品名称
        orderitem.put("UnitPrice", "1.00");//商品总价
        orderitem.put("Qty", "2");//商品数量
        orderitem.put("ProductRemarks", "测试商品"); //商品备注项
        orderitem.put("ProductType", "充值类");//商品类型
        orderitem.put("ProductDiscount", "0.9");//商品折扣
        orderitem.put("ProductExpiredDate", "10");//商品有效期
        tPaymentRequest.orderitems.put(2, orderitem);

//3、生成支付请求对象
        String paymentType = request.getParameter("PaymentType");
        tPaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
        String paymentLinkType = request.getParameter("PaymentLinkType");
        tPaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
        if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE)) {
            tPaymentRequest.dicRequest.put("UnionPayLinkType", request.getParameter("UnionPayLinkType"));  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
        }
        tPaymentRequest.dicRequest.put("ReceiveAccount", request.getParameter("ReceiveAccount"));      //设定收款方账号
        tPaymentRequest.dicRequest.put("ReceiveAccName", request.getParameter("ReceiveAccName"));      //设定收款方户名
        tPaymentRequest.dicRequest.put("NotifyType", request.getParameter("NotifyType"));              //设定通知方式
        tPaymentRequest.dicRequest.put("ResultNotifyURL", request.getParameter("ResultNotifyURL"));    //设定通知URL地址
        tPaymentRequest.dicRequest.put("MerchantRemarks", request.getParameter("MerchantRemarks"));    //设定附言
        tPaymentRequest.dicRequest.put("ReceiveMark", request.getParameter("ReceiveMark"));             //交易是否直接入二级商户账户
        tPaymentRequest.dicRequest.put("ReceiveMerchantType", request.getParameter("ReceiveMerchantType")); //设定收款方账户类型
        tPaymentRequest.dicRequest.put("IsBreakAccount", request.getParameter("IsBreakAccount"));      //设定交易是否分账、交易是否支持向二级商户入账
        tPaymentRequest.dicRequest.put("SplitAccTemplate", request.getParameter("SplitAccTemplate"));  //分账模版编号

//4、添加分账信息
        String[] SubMerchantID_arr = new String[]{};
        String[] SplitAmount_arr = new String[]{};

        SubMerchantID_arr = request.getParameterValues("SplitMerchantID");
        SplitAmount_arr = request.getParameterValues("SplitAmount");

        LinkedHashMap map = null;

        if (SubMerchantID_arr != null) {
            for (int i = 0; i < SubMerchantID_arr.length; i++) {
                map = new LinkedHashMap();
                //map.put("SeqNo       ", String.valueOf(i + 1));
                map.put("SplitMerchantID", SubMerchantID_arr[i]);
                map.put("SplitAmount", SplitAmount_arr[i]);

                tPaymentRequest.dicSplitAccInfo.put(i + 1, map);
            }
        }

        JSON json = tPaymentRequest.postRequest();
//JSON json = tPaymentRequest.extendPostRequest(1);

        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        String OneQRForAll = json.GetKeyValue("OneQRForAll");
        if (ReturnCode.equals("0000")) {
            System.out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            System.out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
            System.out.println("PaymentURL-->" + json.GetKeyValue("PaymentURL"));
            System.out.println("OneQRForAll = [" + OneQRForAll + "]<br/>");
            //response.sendRedirect(json.GetKeyValue("PaymentURL"));
        } else {
            System.out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            System.out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }

    }


    public void ssddf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、生成订单对象
        PaymentRequest tPaymentRequest = new PaymentRequest();
        tPaymentRequest.dicOrder.put("PayTypeID", request.getParameter("PayTypeID"));                   //设定交易类型
        tPaymentRequest.dicOrder.put("OrderDate", request.getParameter("OrderDate"));                  //设定订单日期 （必要信息 - YYYY/MM/DD）
        tPaymentRequest.dicOrder.put("OrderTime", request.getParameter("OrderTime"));                   //设定订单时间 （必要信息 - HH:MM:SS）
        tPaymentRequest.dicOrder.put("orderTimeoutDate", request.getParameter("orderTimeoutDate"));     //设定订单有效期
        tPaymentRequest.dicOrder.put("OrderNo", request.getParameter("OrderNo"));                       //设定订单编号 （必要信息）
        tPaymentRequest.dicOrder.put("CurrencyCode", request.getParameter("CurrencyCode"));             //设定交易币种
        tPaymentRequest.dicOrder.put("OrderAmount", request.getParameter("PaymentRequestAmount"));      //设定交易金额
        tPaymentRequest.dicOrder.put("Fee", request.getParameter("Fee"));                               //设定手续费金额
        tPaymentRequest.dicOrder.put("AccountNo", request.getParameter("AccountNo"));                   //设定支付账户
        tPaymentRequest.dicOrder.put("OrderDesc", request.getParameter("OrderDesc"));                   //设定订单说明
        tPaymentRequest.dicOrder.put("OrderURL", request.getParameter("OrderURL"));                     //设定订单地址
        tPaymentRequest.dicOrder.put("ReceiverAddress", request.getParameter("ReceiverAddress"));       //收货地址
        tPaymentRequest.dicOrder.put("InstallmentMark", request.getParameter("InstallmentMark"));       //分期标识
        if (request.getParameter("InstallmentMark") == "1" && request.getParameter("PayTypeID") == "DividedPay") {
            tPaymentRequest.dicOrder.put("InstallmentCode", request.getParameter("InstallmentCode"));   //设定分期代码
            tPaymentRequest.dicOrder.put("InstallmentNum", request.getParameter("InstallmentNum"));     //设定分期期数
        }
        tPaymentRequest.dicOrder.put("CommodityType", request.getParameter("CommodityType"));           //设置商品种类
        tPaymentRequest.dicOrder.put("BuyIP", request.getParameter("BuyIP"));                           //IP
        tPaymentRequest.dicOrder.put("ExpiredDate", request.getParameter("ExpiredDate"));               //设定订单保存时间

//2、订单明细
        LinkedHashMap orderitem = new LinkedHashMap();
        orderitem.put("SubMerName", "测试二级商户1");    //设定二级商户名称
        orderitem.put("SubMerId", "12345");    //设定二级商户代码
        orderitem.put("SubMerMCC", "0000");   //设定二级商户MCC码
        orderitem.put("SubMerchantRemarks", "测试");   //二级商户备注项
        orderitem.put("ProductID", "IP000001");//商品代码，预留字段
        orderitem.put("ProductName", "中国移动IP卡");//商品名称
        orderitem.put("UnitPrice", "1.00");//商品总价
        orderitem.put("Qty", "1");//商品数量
        orderitem.put("ProductRemarks", "测试商品"); //商品备注项
        orderitem.put("ProductType", "充值类");//商品类型
        orderitem.put("ProductDiscount", "0.9");//商品折扣
        orderitem.put("ProductExpiredDate", "10");//商品有效期
        tPaymentRequest.orderitems.put(1, orderitem);

        orderitem = new LinkedHashMap();
        orderitem.put("SubMerName", "测试二级商户1");    //设定二级商户名称
        orderitem.put("SubMerId", "12345");    //设定二级商户代码
        orderitem.put("SubMerMCC", "0000");   //设定二级商户MCC码
        orderitem.put("SubMerchantRemarks", "测试");   //二级商户备注项
        orderitem.put("ProductID", "IP000001");//商品代码，预留字段
        orderitem.put("ProductName", "中国移动IP卡");//商品名称
        orderitem.put("UnitPrice", "1.00");//商品总价
        orderitem.put("Qty", "2");//商品数量
        orderitem.put("ProductRemarks", "测试商品"); //商品备注项
        orderitem.put("ProductType", "充值类");//商品类型
        orderitem.put("ProductDiscount", "0.9");//商品折扣
        orderitem.put("ProductExpiredDate", "10");//商品有效期
        tPaymentRequest.orderitems.put(2, orderitem);

        //3、生成支付请求对象
        String paymentType = request.getParameter("PaymentType");
        tPaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
        String paymentLinkType = request.getParameter("PaymentLinkType");
        tPaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
        if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE)) {
            tPaymentRequest.dicRequest.put("UnionPayLinkType", request.getParameter("UnionPayLinkType"));  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
        }
        tPaymentRequest.dicRequest.put("ReceiveAccount", request.getParameter("ReceiveAccount"));    //设定收款方账号
        tPaymentRequest.dicRequest.put("ReceiveAccName", request.getParameter("ReceiveAccName"));    //设定收款方户名
        tPaymentRequest.dicRequest.put("NotifyType", request.getParameter("NotifyType"));    //设定通知方式
        tPaymentRequest.dicRequest.put("ResultNotifyURL", request.getParameter("ResultNotifyURL"));    //设定通知URL地址
        tPaymentRequest.dicRequest.put("MerchantRemarks", request.getParameter("MerchantRemarks"));    //设定附言
        tPaymentRequest.dicRequest.put("IsBreakAccount", request.getParameter("IsBreakAccount"));    //设定交易是否分账
        tPaymentRequest.dicRequest.put("SplitAccTemplate", request.getParameter("SplitAccTemplate"));      //分账模版编号


        MerchantPara para = null;
        try {
            para = MerchantConfig.getUniqueInstance().getPara();
        } catch (TrxException e) {
            e.printStackTrace();
        }
        String sTrustPayIETrxURL = para.getTrustPayTrxIEURL();
        String sErrorUrl = para.getMerchantErrorURL();
        String tSignature = "";
        try {
            tSignature = tPaymentRequest.genSignature(1);
        } catch (TrxException e) {
            request.setAttribute("tReturnCode", e.getCode());
            request.setAttribute("tErrorMsg", e.getMessage());
            request.getRequestDispatcher("/ErrorPageInternal.jsp").forward(request, response);
            return;
        }
    }

    // 支付回调
    public void MerchantResult(HttpServletRequest request) throws TrxException {
        //1、取得MSG参数，并利用此参数值生成支付结果对象
        String msg = request.getParameter("MSG");
        PaymentResult tResult = new PaymentResult(msg);

        //2、判断支付结果状态，进行后续操作
        if (tResult.isSuccess()) {
            //3、支付成功并且验签、解析成功
            System.out.println("TrxType         = [" + tResult.getValue("TrxType") + "]<br>");
            System.out.println("OrderNo         = [" + tResult.getValue("OrderNo") + "]<br>");
            System.out.println("Amount          = [" + tResult.getValue("Amount") + "]<br>");
            System.out.println("BatchNo         = [" + tResult.getValue("BatchNo") + "]<br>");
            System.out.println("VoucherNo       = [" + tResult.getValue("VoucherNo") + "]<br>");
            System.out.println("HostDate        = [" + tResult.getValue("HostDate") + "]<br>");
            System.out.println("HostTime        = [" + tResult.getValue("HostTime") + "]<br>");
            System.out.println("MerchantRemarks = [" + tResult.getValue("MerchantRemarks") + "]<br>");
            System.out.println("PayType         = [" + tResult.getValue("PayType") + "]<br>");
            System.out.println("NotifyType      = [" + tResult.getValue("NotifyType") + "]<br>");
            System.out.println("TrnxNo          = [" + tResult.getValue("iRspRef") + "]<br>");
            System.out.println("BankType        = [" + tResult.getValue("bank_type") + "]<br>");
            System.out.println("ThirdOrderNo    = [" + tResult.getValue("ThirdOrderNo") + "]<br>");
        } else {
            //4、支付成功但是由于验签或者解析报文等操作失败
            System.out.println("ReturnCode   = [" + tResult.getReturnCode() + "]<br>");
            System.out.println("ErrorMessage = [" + tResult.getErrorMessage() + "]<br>");
        }
    }


    // 单订单退款

    public void MerchantRefund(HttpServletRequest request) {

        // 查询订单信息

        //1、生成退款请求对象
        RefundRequest tRequest = new RefundRequest();
        tRequest.dicRequest.put("OrderDate", request.getParameter("txtOrderDate"));  //订单日期（必要信息）
        tRequest.dicRequest.put("OrderTime", request.getParameter("txtOrderTime")); //订单时间（必要信息）
        //tRequest.dicRequest.put("MerRefundAccountNo", request.getParameter("txtMerRefundAccountNo"));  //商户退款账号
        //tRequest.dicRequest.put("MerRefundAccountName", request.getParameter("txtMerRefundAccountName")); //商户退款名
        tRequest.dicRequest.put("OrderNo", request.getParameter("txtOrderNo")); //原交易编号（必要信息）
        tRequest.dicRequest.put("NewOrderNo", request.getParameter("txtNewOrderNo")); //交易编号（必要信息）
        tRequest.dicRequest.put("CurrencyCode", request.getParameter("txtCurrencyCode")); //交易币种（必要信息）
        tRequest.dicRequest.put("TrxAmount", request.getParameter("txtTrxAmount")); //退货金额 （必要信息）
        tRequest.dicRequest.put("RefundType", request.getParameter("txtRefundType")); //退货类型 （非必要信息）
        tRequest.dicRequest.put("MerchantRemarks", request.getParameter("txtMerchantRemarks"));  //附言
        tRequest.dicRequest.put("MerRefundAccountFlag", request.getParameter("txtMerRefundAccountFlag"));  //退款账簿上送标识 1：担保账户 2：商户二级账簿 3：退款账簿
        //如果需要专线地址，调用此方法：
        //tRequest.setConnectionFlag(true);

        String[] SubMerchantID_arr = new String[]{};
        String[] SplitAmount_arr = new String[]{};

        SubMerchantID_arr = request.getParameterValues("SplitMerchantID");
        SplitAmount_arr = request.getParameterValues("SplitAmount");

        LinkedHashMap map = null;

        if (SubMerchantID_arr != null) {
            for (int i = 0; i < SubMerchantID_arr.length; i++) {
                map = new LinkedHashMap();
                //map.put("SeqNo       ", String.valueOf(i + 1));
                map.put("SplitMerchantID", SubMerchantID_arr[i]);
                map.put("SplitAmount", SplitAmount_arr[i]);

                tRequest.dicSplitAccInfo.put(i + 1, map);
            }
        }

        //3、传送退款请求并取得退货结果
        JSON json = tRequest.postRequest();

        //4、判断退款结果状态，进行后续操作
        StringBuilder strMessage = new StringBuilder("");
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        if (ReturnCode.equals("0000")) {
            //5、退款成功/退款受理成功
            System.out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            System.out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
            System.out.println("OrderNo   = [" + json.GetKeyValue("OrderNo") + "]<br/>");
            System.out.println("NewOrderNo   = [" + json.GetKeyValue("NewOrderNo") + "]<br/>");
            System.out.println("TrxAmount = [" + json.GetKeyValue("TrxAmount") + "]<br/>");
            System.out.println("BatchNo   = [" + json.GetKeyValue("BatchNo") + "]<br/>");
            System.out.println("VoucherNo = [" + json.GetKeyValue("VoucherNo") + "]<br/>");
            System.out.println("HostDate  = [" + json.GetKeyValue("HostDate") + "]<br/>");
            System.out.println("HostTime  = [" + json.GetKeyValue("HostTime") + "]<br/>");
            System.out.println("iRspRef  = [" + json.GetKeyValue("iRspRef") + "]<br/>");
        } else {
            //6、退款失败
            System.out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            System.out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    }


    //批量退款
    public void MerchantBatchRefund(HttpServletRequest request) {
        StringBuilder strMessage = new StringBuilder("");
        //验证输入信息并取得退款所需要的信息
        String totalCount = request.getParameter("TotalCount");

        String[] orderno_arr = null;
        String[] neworderno_arr = null;
        String[] currencycode_arr = null;
        String[] orderamount_arr = null;
        String[] remark_arr = null;

        int batchSize = Integer.parseInt(totalCount);
        if (batchSize == 1) {
            String orderno = request.getParameter("txtOrderNo");
            String neworderno = request.getParameter("txtNewOrderNo");
            String currencycode = request.getParameter("txtCurrencyCode");
            String orderamount = request.getParameter("txtRefundAmount");
            String remark = request.getParameter("txtRemark");
            orderno_arr = new String[]{orderno};
            neworderno_arr = new String[]{neworderno};
            currencycode_arr = new String[]{currencycode};
            orderamount_arr = new String[]{orderamount};
            remark_arr = new String[]{remark};
        } else {
            orderno_arr = request.getParameterValues("txtOrderNo");
            neworderno_arr = request.getParameterValues("txtNewOrderNo");
            currencycode_arr = request.getParameterValues("txtCurrencyCode");
            orderamount_arr = request.getParameterValues("txtRefundAmount");
            remark_arr = request.getParameterValues("txtRemark");
        }

        //1、生成批量退款请求对象
        BatchRefundRequest tBatchRefundRequest = new BatchRefundRequest();
        //取得明细项
        LinkedHashMap map = null;
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < orderno_arr.length; i++) {
            map = new LinkedHashMap();
            map.put("SeqNo", String.valueOf(i + 1));
            map.put("OrderNo", orderno_arr[i]);
            map.put("NewOrderNo", neworderno_arr[i]);
            map.put("CurrencyCode", currencycode_arr[i]);
            map.put("RefundAmount", orderamount_arr[i]);
            map.put("Remark", remark_arr[i]);
            tBatchRefundRequest.dic.put(i + 1, map);
            //此处必须使用BigDecimal，否则会丢精度
            BigDecimal bd = new BigDecimal(orderamount_arr[i].toString());
            sum = sum.add(bd);
        }
        //此处必须设定iSumAmount属性
        tBatchRefundRequest.iSumAmount = sum.doubleValue();

        tBatchRefundRequest.batchRefundRequest.put("BatchNo", request.getParameter("txtBatchNo")); //批量编号  （必要信息）
        tBatchRefundRequest.batchRefundRequest.put("BatchDate", request.getParameter("txtBatchDate"));  //订单日期  （必要信息）
        tBatchRefundRequest.batchRefundRequest.put("BatchTime", request.getParameter("txtBatchTime")); //订单时间  （必要信息）
        tBatchRefundRequest.batchRefundRequest.put("MerRefundAccountNo", request.getParameter("txtMerRefundAccountNo"));  //商户退款账号
        tBatchRefundRequest.batchRefundRequest.put("MerRefundAccountName", request.getParameter("txtMerRefundAccountName")); //商户退款名
        tBatchRefundRequest.batchRefundRequest.put("TotalCount", request.getParameter("TotalCount"));  //总笔数  （必要信息）
        tBatchRefundRequest.batchRefundRequest.put("TotalAmount", request.getParameter("TotalAmount"));  //总金额 （必要信息）

        //2、传送批量退款请求并取得结果
        JSON json = tBatchRefundRequest.postRequest();

        //3、判断批量退款结果状态，进行后续操作
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        if (ReturnCode.equals("0000")) {
            //4、批量退款成功
            System.out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            System.out.println("ResultMessage   = [" + ErrorMessage + "]<br/>");
            System.out.println("TrxType   = [" + json.GetKeyValue("TrxType") + "]<br/>");
            System.out.println("TotalCount  = [" + json.GetKeyValue("TotalCount") + "]<br/>");
            System.out.println("TotalAmount = [" + json.GetKeyValue("TotalAmount") + "]<br/>");
            System.out.println("SerialNumber  = [" + json.GetKeyValue("SerialNumber") + "]<br/>");
            System.out.println("HostDate  = [" + json.GetKeyValue("HostDate") + "]<br/>");
            System.out.println("HostTime  = [" + json.GetKeyValue("HostTime") + "]<br/>");
        } else {
            //5、批量退款失败
            System.out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            System.out.println("ResultMessage   = [" + ErrorMessage + "]<br/>");
        }

    }

    public void result(HttpServletRequest request) throws TrxException {
        String msg = request.getParameter("MSG");
        PaymentResult tResult = new PaymentResult(msg);

//2、判断支付结果状态，进行后续操作
        if (tResult.isSuccess()) {
            //3、支付成功并且验签、解析成功
            System.out.println("TrxType         = [" + tResult.getValue("TrxType") + "]<br>");
            System.out.println("OrderNo         = [" + tResult.getValue("OrderNo") + "]<br>");
            System.out.println("Amount          = [" + tResult.getValue("Amount") + "]<br>");
            System.out.println("BatchNo         = [" + tResult.getValue("BatchNo") + "]<br>");
            System.out.println("VoucherNo       = [" + tResult.getValue("VoucherNo") + "]<br>");
            System.out.println("HostDate        = [" + tResult.getValue("HostDate") + "]<br>");
            System.out.println("HostTime        = [" + tResult.getValue("HostTime") + "]<br>");
            System.out.println("MerchantRemarks = [" + tResult.getValue("MerchantRemarks") + "]<br>");
            System.out.println("PayType         = [" + tResult.getValue("PayType") + "]<br>");
            System.out.println("NotifyType      = [" + tResult.getValue("NotifyType") + "]<br>");
            System.out.println("TrnxNo          = [" + tResult.getValue("iRspRef") + "]<br>");
            System.out.println("BankType        = [" + tResult.getValue("bank_type") + "]<br>");
            System.out.println("ThirdOrderNo    = [" + tResult.getValue("ThirdOrderNo") + "]<br>");
        } else {
            //4、支付成功但是由于验签或者解析报文等操作失败
            System.out.println("ReturnCode   = [" + tResult.getReturnCode() + "]<br>");
            System.out.println("ErrorMessage = [" + tResult.getErrorMessage() + "]<br>");
        }
    }

}
