<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.*" %>
<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-�����ײ�ѯ</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>�����ײ�ѯ<br>
    <%
        //������ת��ѯ����
        String TransferNo = request.getParameter("TransferNo");

        TransferOutQueryRequest transferQueryRequest = new TransferOutQueryRequest();
        transferQueryRequest.queryRequest.put("TransferNo", request.getParameter("TransferNo"));
        JSON json = transferQueryRequest.postRequest();

        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        String Status = json.GetKeyValue("Status");

        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
        out.println("Status   = [" + Status + "]<br/>");

    %>


    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>