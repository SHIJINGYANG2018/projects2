<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.LinkedHashMap" %>
<%
    request.setCharacterEncoding("GBK");
    response.setHeader("Cache-Control", "no-cache");
%>
<%
    //1������֧���������
    SubMerAccBalQryRequest tPaymentRequest = new SubMerAccBalQryRequest();

    tPaymentRequest.dicRequest.put("SubMerId", request.getParameter("SubMerId"));        //ת�˽��ױ��

    JSON json = tPaymentRequest.postRequest();
//JSON json = tPaymentRequest.extendPostRequest(1);

    String ReturnCode = json.GetKeyValue("ReturnCode");
    String ErrorMessage = json.GetKeyValue("ErrorMessage");
    String MerchantName = json.GetKeyValue("MerchantName");
    String SubMerId = json.GetKeyValue("SubMerId");
    String SubMerchantAccNo = json.GetKeyValue("SubMerchantAccNo");
    String Balance = json.GetKeyValue("Balance");

    if (ReturnCode.equals("0000")) {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");

        out.println("MerchantName = [" + MerchantName + "]<br/>");            //���̻�����
        out.println("SubMerId = [" + SubMerId + "]<br/>");                    //�����̻���
        out.println("SubMerchantAccNo = [" + SubMerchantAccNo + "]<br/>");    //�����̻��˺�
        out.println("Balance = [" + Balance + "]<br/>");                      //�����̻����

    } else {

%>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-֧������</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>֧������<br>
    <%
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>

    <a href='MerchantUnified.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>