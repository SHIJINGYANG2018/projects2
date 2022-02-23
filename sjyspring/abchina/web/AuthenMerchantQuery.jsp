<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.*" %>
<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-鉴权查询</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>鉴权查询<br>
    <%
        //生成鉴权查询对象
        String TransferNo = request.getParameter("TransferNo");

        AuthenMerchantQueryRequest authenQueryRequest = new AuthenMerchantQueryRequest();
        authenQueryRequest.queryRequest.put("TransferNo", request.getParameter("TransferNo"));
        JSON json = authenQueryRequest.postRequest();

        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        String Status = json.GetKeyValue("Status");

        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
        out.println("Status   = [" + Status + "]<br/>");

    %>


    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>