<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-订单撤销</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>商户订单撤销<br>
    <%
        //request.setCharacterEncoding("GBK");

//NumberFormat tFormat = NumberFormat.getInstance();
//tFormat.setMaximumFractionDigits(2);
//tFormat.setGroupingUsed(false);
//tFormat.setMinimumFractionDigits(2);

//1、取得商户订单撤销所需要的信息
        String orderNo = request.getParameter("OrderNo");
        String subMchNO = request.getParameter("SubMchNO");
        String modelFlag = request.getParameter("ModelFlag");
        String merchantFlag = request.getParameter("MerchantFlag");

//2、商户订单撤销请求对象
        PayCancelRequest tRequest = new PayCancelRequest();
        tRequest.dicRequest.put("OrderNo", orderNo);
        tRequest.dicRequest.put("SubMchNO", subMchNO);
        tRequest.dicRequest.put("ModelFlag", modelFlag);
        tRequest.dicRequest.put("MerchantFlag", merchantFlag);

//3、传送商户订单撤销请求
        JSON json = tRequest.postRequest();

//4、判断结果状态，进行后续操作
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {
            //5、商户订单撤销成功
            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");
            out.println("OrderInfo      = [" + json.GetKeyValue("OrderInfo") + "]<br/>");


        } else {
            //6、商户订单撤销失败
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
            out.println("OrderInfo    = [" + json.GetKeyValue("OrderInfo") + "]<br/>");
        }
    %>
    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>