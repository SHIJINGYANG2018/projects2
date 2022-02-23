<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<% response.setHeader("Cache-Control", "no-cache");
    request.setCharacterEncoding("GBK");
%>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-二级商户信息同步</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>支付请求请求<br>
    <%
        //1、生成支付请求对象
        RegSubMerchantInfoRequest tMerchantInfo = new RegSubMerchantInfoRequest();

        //2、设置请求值
        //tMerchantInfo.merchantInfoRequest.put("MerchantID", request.getParameter("txtMerchantID")); 
        tMerchantInfo.merchantInfoRequest.put("SubMerId", request.getParameter("txtSubMerId"));
        tMerchantInfo.merchantInfoRequest.put("SubMerName", request.getParameter("txtSubMerName"));
        tMerchantInfo.merchantInfoRequest.put("SubMerType", request.getParameter("txtSubMerType"));
        tMerchantInfo.merchantInfoRequest.put("Status", request.getParameter("txtStatus"));
        //tMerchantInfo.merchantInfoRequest.put("SubMerSort", request.getParameter("txtSubMerSort")); 
        //tMerchantInfo.merchantInfoRequest.put("MCC", request.getParameter("txtMCC")); 
        tMerchantInfo.merchantInfoRequest.put("ContactName", request.getParameter("txtContactName"));
        tMerchantInfo.merchantInfoRequest.put("CertificateType", request.getParameter("txtCertificateType"));
        tMerchantInfo.merchantInfoRequest.put("CertificateNo", request.getParameter("txtCertificateNo"));

        //tMerchantInfo.merchantInfoRequest.put("MobileNo", request.getParameter("txtMobileNo")); 
        tMerchantInfo.merchantInfoRequest.put("CompanyName", request.getParameter("txtMerchantName"));
        tMerchantInfo.merchantInfoRequest.put("CompanyCertType", request.getParameter("txtMerCertificateType"));
        tMerchantInfo.merchantInfoRequest.put("CompanyCertNo", request.getParameter("txtMerCertificateNum"));

        tMerchantInfo.merchantInfoRequest.put("AccountName", request.getParameter("txtReceiveAccountName"));
        tMerchantInfo.merchantInfoRequest.put("Account", request.getParameter("txtReceiveAccount"));
        tMerchantInfo.merchantInfoRequest.put("BankName", request.getParameter("txtReceiveBankName"));

        tMerchantInfo.merchantInfoRequest.put("MobilePhone", request.getParameter("txtBankMobileNum"));
        tMerchantInfo.merchantInfoRequest.put("AccountType", request.getParameter("txtReceiveAccountType"));
        tMerchantInfo.merchantInfoRequest.put("Address", request.getParameter("txtAddress"));
        tMerchantInfo.merchantInfoRequest.put("Remark", request.getParameter("txtRemark"));

        //4、传送请求并返回结果
        JSON json = tMerchantInfo.postRequest();

        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        if (ReturnCode.equals("0000")) {
            //5、请求提交成功，返回结果信息
            out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
            //strMessage.append("ECMerchantType   = [" + json.GetKeyValue("ECMerchantType") + "]<br/>");
            out.println("MerchantID = [" + json.GetKeyValue("MerchantID") + "]<br/>");
            out.println("TrxType = [" + json.GetKeyValue("TrxType") + "]<br/>");
            out.println("TransferNo = [" + json.GetKeyValue("TransferNo") + "]<br/>");
            //out.println("ResRemark = [" + json.GetKeyValue("ResRemark") + "]<br/>");
        } else {
            //6、请求提交失败，商户自定后续动作
            out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
        }
    %>

    <a href='MerchantUnified.html'>回商户首页</a></CENTER>
</BODY>
</HTML>