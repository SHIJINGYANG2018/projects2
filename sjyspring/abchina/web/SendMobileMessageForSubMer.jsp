<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-�����̻�������֤���ط�</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>�����̻�������֤���ط�<br>
    <%
        //request.setCharacterEncoding("GBK");


//1��ȡ��������Ϣ
        String subMerId = request.getParameter("SubMerId");
        String subMerchantAccNo = request.getParameter("SubMerchantAccNo");


//2�������������
        SendMobileMessageForSubMerRequest tRequest = new SendMobileMessageForSubMerRequest();
        tRequest.dicRequest.put("SubMerId", subMerId);
        tRequest.dicRequest.put("SubMerchantAccNo", subMerchantAccNo);


//3����������
        JSON json = tRequest.postRequest();

//4���жϽ��״̬�����к�������
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {

            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");
            out.println("TrxType      = [" + json.GetKeyValue("TrxType") + "]<br/>");

        } else {
            //ʧ��
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>
    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>