<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.LinkedHashMap" %>
<%
    request.setCharacterEncoding("GBK");
    response.setHeader("Cache-Control", "no-cache");
%>
<%
    //1、生成订单对象
    DepositRequest tPaymentRequest = new DepositRequest();
    tPaymentRequest.dicOrder.put("orderTimeoutDate", request.getParameter("orderTimeoutDate"));     //设定订单有效期
    tPaymentRequest.dicOrder.put("CurrencyCode", request.getParameter("CurrencyCode"));             //设定交易币种
    tPaymentRequest.dicOrder.put("OrderAmount", request.getParameter("PaymentRequestAmount"));      //设定交易金额
    tPaymentRequest.dicOrder.put("OrderDesc", request.getParameter("OrderDesc"));                   //设定订单说明

//3、生成支付请求对象
    String paymentLinkType = request.getParameter("PaymentLinkType");
    tPaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式

    tPaymentRequest.dicRequest.put("NotifyType", request.getParameter("NotifyType"));              //设定通知方式
    tPaymentRequest.dicRequest.put("ResultNotifyURL", request.getParameter("ResultNotifyURL"));    //设定通知URL地址
    tPaymentRequest.dicRequest.put("MerchantRemarks", request.getParameter("MerchantRemarks"));    //设定附言
    tPaymentRequest.dicRequest.put("ReceiveSubMerchantNo", request.getParameter("ReceiveSubMerchantNo")); //设定收款子商户号


    JSON json = tPaymentRequest.postRequest();
//JSON json = tPaymentRequest.extendPostRequest(1);

    String ReturnCode = json.GetKeyValue("ReturnCode");
    String ErrorMessage = json.GetKeyValue("ErrorMessage");
    if (ReturnCode.equals("0000")) {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
        //response.sendRedirect(json.GetKeyValue("PaymentURL"));
    } else {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
//tPaymentRequest.extendPostRequest(1);
%>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-充值请求</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>充值请求<br>
    <%
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>

    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>