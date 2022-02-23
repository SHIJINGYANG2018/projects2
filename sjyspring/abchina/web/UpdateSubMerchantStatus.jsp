<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-修改二级商户状态</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>修改二级商户状态<br>
    <%
        //request.setCharacterEncoding("GBK");

//NumberFormat tFormat = NumberFormat.getInstance();
//tFormat.setMaximumFractionDigits(2);
//tFormat.setGroupingUsed(false);
//tFormat.setMinimumFractionDigits(2);

//1、取得修改二级商户状态所需要的信息
        String subMerId = request.getParameter("SubMerId");
        String updateFlag = request.getParameter("UpdateFlag");


//2、修改二级商户状态请求对象
        UpdateSubMerchantStatusRequest tRequest = new UpdateSubMerchantStatusRequest();
        tRequest.dicRequest.put("SubMerId", subMerId);
        tRequest.dicRequest.put("UpdateFlag", updateFlag);


//3、传送请求
        JSON json = tRequest.postRequest();

//4、判断结果状态，进行后续操作
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {
            //5、商户订单撤销成功
            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");

        } else {
            //6、商户订单撤销失败
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>
    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>