<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-担保确认查询接口</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>担保确认查询接口<br>
    <%
        //request.setCharacterEncoding("GBK");

//NumberFormat tFormat = NumberFormat.getInstance();
//tFormat.setMaximumFractionDigits(2);
//tFormat.setGroupingUsed(false);
//tFormat.setMinimumFractionDigits(2);

        String orderNo = request.getParameter("OrderNo");
        String orignalOrderNo = request.getParameter("OrignalOrderNo");
        String subMerchantNo = request.getParameter("SubMerchantNo");


        QueryPlatformConfirmRequest tRequest = new QueryPlatformConfirmRequest();
        tRequest.dicRequest.put("OrderNo", orderNo);
        tRequest.dicRequest.put("OrignalOrderNo", orignalOrderNo);
        tRequest.dicRequest.put("SubMerchantNo", subMerchantNo);


//传送请求
        JSON json = tRequest.postRequest();

//4、判断结果状态，进行后续操作
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {
            //成功
            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");
            out.println("TrxType      = [" + json.GetKeyValue("TrxType") + "]<br/>");
            out.println("ResultCode      = [" + json.GetKeyValue("ResultCode") + "]<br/>");
            out.println("ResultMessage      = [" + json.GetKeyValue("ResultMessage") + "]<br/>");

        } else {
            //失败
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>
    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>