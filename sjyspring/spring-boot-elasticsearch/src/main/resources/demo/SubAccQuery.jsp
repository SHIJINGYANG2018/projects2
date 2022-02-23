<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.LinkedHashMap" %>
<%
    request.setCharacterEncoding("GBK");
    response.setHeader("Cache-Control", "no-cache");
%>
<%
    //1、生成支付请求对象
    SubMerAccBalQryRequest tPaymentRequest = new SubMerAccBalQryRequest();

    tPaymentRequest.dicRequest.put("SubMerId", request.getParameter("SubMerId"));        //转账交易编号

    JSON json = tPaymentRequest.postRequest();
//JSON json = tPaymentRequest.extendPostRequest(1);

    String ReturnCode = json.GetKeyValue("ReturnCode");
    String ErrorMessage = json.GetKeyValue("ErrorMessage");
    String MerchantName = json.GetKeyValue("MerchantName");
    String SubMerId = json.GetKeyValue("SubMerId");
    String SubMerchantAccNo = json.GetKeyValue("SubMerchantAccNo");
    String Balance = json.GetKeyValue("Balance");

    if (ReturnCode.equals("0000")) {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");

        out.println("MerchantName = [" + MerchantName + "]<br/>");            //主商户名称
        out.println("SubMerId = [" + SubMerId + "]<br/>");                    //二级商户号
        out.println("SubMerchantAccNo = [" + SubMerchantAccNo + "]<br/>");    //二级商户账号
        out.println("Balance = [" + Balance + "]<br/>");                      //二级商户余额

    } else {

%>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-支付请求</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>支付请求<br>
    <%
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>

    <a href='MerchantUnified.html'>回商户首页</a></CENTER>
</BODY>
</HTML>