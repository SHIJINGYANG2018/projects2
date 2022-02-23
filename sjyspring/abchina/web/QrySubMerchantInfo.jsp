<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.*" %>
<% response.setHeader("Cache-Control", "no-cache"); %>
<%
    //生成交易查询对象
    String subMerId = request.getParameter("SubMerId");

    QrySubMerchantInfoRequest tQueryRequest = new QrySubMerchantInfoRequest();
    tQueryRequest.queryRequest.put("SubMerId", subMerId);    //设定二级商户号

//如果需要专线地址，调用此方法：
//tQueryRequest.setConnectionFlag(true);
    JSON json = tQueryRequest.postRequest();

    String ReturnCode = json.GetKeyValue("ReturnCode");
    String ErrorMessage = json.GetKeyValue("ErrorMessage");

    String MerchantName = json.GetKeyValue("MerchantName");
    String SubMerId = json.GetKeyValue("SubMerId");
    String SubMerName = json.GetKeyValue("SubMerName");
    String SubMerSort = json.GetKeyValue("SubMerSort");
    String SubMerchantType = json.GetKeyValue("SubMerchantType");
    String CertificateType = json.GetKeyValue("CertificateType");
    String CertificateNo = json.GetKeyValue("CertificateNo");
    String ContactName = json.GetKeyValue("ContactName");
    String MobileNo = json.GetKeyValue("MobileNo");
    String Status = json.GetKeyValue("Status");
    String StatusMessage = json.GetKeyValue("StatusMessage");
    String CompanyCertType = json.GetKeyValue("CompanyCertType");
    String CompanyCertNo = json.GetKeyValue("CompanyCertNo");
    String CompanyName = json.GetKeyValue("CompanyName");
    String NotifyUrl = json.GetKeyValue("NotifyUrl");
    String SubMerSignNo = json.GetKeyValue("SubMerSignNo");
    String isPassed = json.GetKeyValue("isPassed");

    if (ReturnCode.equals("0000")) {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
        //获取结果信息
        out.println("MerchantName = [" + MerchantName + "]<br/>");
        out.println("SubMerId = [" + SubMerId + "]<br/>");
        out.println("SubMerName = [" + SubMerName + "]<br/>");
        out.println("SubMerSort = [" + SubMerSort + "]<br/>");
        out.println("SubMerchantType = [" + SubMerchantType + "]<br/>");
        out.println("CertificateType = [" + CertificateType + "]<br/>");
        out.println("CertificateNo = [" + CertificateNo + "]<br/>");
        out.println("ContactName = [" + ContactName + "]<br/>");
        out.println("MobileNo = [" + MobileNo + "]<br/>");
        out.println("Status = [" + Status + "]<br/>");
        out.println("StatusMessage = [" + StatusMessage + "]<br/>");
        out.println("CompanyCertType = [" + CompanyCertType + "]<br/>");
        out.println("CompanyCertNo = [" + CompanyCertNo + "]<br/>");
        out.println("CompanyName = [" + CompanyName + "]<br/>");
        out.println("NotifyUrl = [" + NotifyUrl + "]<br/>");
        out.println("SubMerSignNo = [" + SubMerSignNo + "]<br/>");
        out.println("isPassed = [" + isPassed + "]<br/>");

    } else {
        //二级商户查询失败
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
    }
%>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-查询二级商户信息</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>二级商户信息查询<br>

    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>