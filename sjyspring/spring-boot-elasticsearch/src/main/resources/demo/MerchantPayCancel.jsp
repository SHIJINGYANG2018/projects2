<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-��������</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>�̻���������<br>
    <%
        //request.setCharacterEncoding("GBK");

//NumberFormat tFormat = NumberFormat.getInstance();
//tFormat.setMaximumFractionDigits(2);
//tFormat.setGroupingUsed(false);
//tFormat.setMinimumFractionDigits(2);

//1��ȡ���̻�������������Ҫ����Ϣ
        String orderNo = request.getParameter("OrderNo");
        String subMchNO = request.getParameter("SubMchNO");
        String modelFlag = request.getParameter("ModelFlag");
        String merchantFlag = request.getParameter("MerchantFlag");

//2���̻����������������
        PayCancelRequest tRequest = new PayCancelRequest();
        tRequest.dicRequest.put("OrderNo", orderNo);
        tRequest.dicRequest.put("SubMchNO", subMchNO);
        tRequest.dicRequest.put("ModelFlag", modelFlag);
        tRequest.dicRequest.put("MerchantFlag", merchantFlag);

//3�������̻�������������
        JSON json = tRequest.postRequest();

//4���жϽ��״̬�����к�������
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {
            //5���̻����������ɹ�
            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");
            out.println("OrderInfo      = [" + json.GetKeyValue("OrderInfo") + "]<br/>");


        } else {
            //6���̻���������ʧ��
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
            out.println("OrderInfo    = [" + json.GetKeyValue("OrderInfo") + "]<br/>");
        }
    %>
    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>