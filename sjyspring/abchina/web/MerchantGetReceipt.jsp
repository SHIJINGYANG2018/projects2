<%@ page language="java" import="java.util.*" pageEncoding="gb2312" %>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.*" %>
<% response.setHeader("Cache-Control", "no-cache"); %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

//1、生成交易查询对象
    String subMerchantNo = request.getParameter("SubMerchantNo");
    String orderNo = request.getParameter("OrderNo");


    GetReceiptRequest tReceiptRequest = new GetReceiptRequest();
    tReceiptRequest.receiptRequest.put("SubMerchantNo", subMerchantNo);    //设定交易类型
    tReceiptRequest.receiptRequest.put("OrderNo", orderNo);    //设定订单编号 （必要信息）

//如果需要专线地址，调用此方法：
//tQueryRequest.setConnectionFlag(true);
    JSON json = tReceiptRequest.postRequest();
//JSON json = tQueryRequest.extendPostRequest(1);

    String ReturnCode = json.GetKeyValue("ReturnCode");
    String ErrorMessage = json.GetKeyValue("ErrorMessage");

    if (ReturnCode.equals("0000")) {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
        //4、获取结果信息
        String receiptStr = json.GetKeyValue("ImageCode");
        byte[] imageBytes = tReceiptRequest.decompressFromBase64String(receiptStr);

        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + orderNo + ".bmp");
        //ServletOutputStream outReceipt = response.getOutputStream();
        response.getOutputStream().write(imageBytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();

        //为防止抛出IllegalStateException,增加下面的两行代码
        //Servlet中规定response.getWriter()和response.getOutputStream()生成的对象，不能同时调用
        out.clear();
        out = pageContext.pushBody();
    } else {
        //5、商户请求下载电子回单失败
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
    }
%>

<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-电子回单下载</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>电子回单下载<br>

    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>
