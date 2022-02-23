<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-二级商户迁移接口</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>二级商户迁移接口<br>
    <%
        //1、取得二级商户迁移所需要的信息
        String fromMerchantNo = request.getParameter("FromMerchantNo");
        String subMerchantNo = request.getParameter("SubMerchantNo");

//2、创建请求对象
        MigrateSubMerchantInfoRequest tRequest = new MigrateSubMerchantInfoRequest();
        tRequest.dicRequest.put("FromMerchantNo", fromMerchantNo);
        tRequest.dicRequest.put("SubMerchantNo", subMerchantNo);

//3、提交请求
        JSON json = tRequest.postRequest();

//4、判断结果状态，进行后续操作
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {
            //5请求成功
            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");

        } else {
            //6、请求失败
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");

        }
    %>
    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>