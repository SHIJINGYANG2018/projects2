<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-短信验证码&随机金额确认接口</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>短信验证码&随机金额确认接口<br>
    <%
        //request.setCharacterEncoding("GBK");

//NumberFormat tFormat = NumberFormat.getInstance();
//tFormat.setMaximumFractionDigits(2);
//tFormat.setGroupingUsed(false);
//tFormat.setMinimumFractionDigits(2);

        String subMerchantNo = request.getParameter("SubMerchantNo");
        String verificationCode = request.getParameter("VerificationCode");
        String randomAmount = request.getParameter("RandomAmount");
        String account = request.getParameter("Account");

        VerifyMessageCodeAndRandomAmount tRequest = new VerifyMessageCodeAndRandomAmount();
        tRequest.dicRequest.put("SubMerchantNo", subMerchantNo);
        tRequest.dicRequest.put("VerificationCode", verificationCode);
        tRequest.dicRequest.put("RandomAmount", randomAmount);
        tRequest.dicRequest.put("Account", account);


//传送请求
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