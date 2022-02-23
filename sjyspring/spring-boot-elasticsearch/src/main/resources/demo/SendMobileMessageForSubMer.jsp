<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-二级商户短信验证码重发</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>二级商户短信验证码重发<br>
    <%
        //request.setCharacterEncoding("GBK");


//1、取得所需信息
        String subMerId = request.getParameter("SubMerId");
        String subMerchantAccNo = request.getParameter("SubMerchantAccNo");


//2、创建请求对象
        SendMobileMessageForSubMerRequest tRequest = new SendMobileMessageForSubMerRequest();
        tRequest.dicRequest.put("SubMerId", subMerId);
        tRequest.dicRequest.put("SubMerchantAccNo", subMerchantAccNo);


//3、传送请求
        JSON json = tRequest.postRequest();

//4、判断结果状态，进行后续操作
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {

            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");
            out.println("TrxType      = [" + json.GetKeyValue("TrxType") + "]<br/>");

        } else {
            //失败
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>
    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>