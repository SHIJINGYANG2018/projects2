<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-������֤��&������ȷ�Ͻӿ�</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>������֤��&������ȷ�Ͻӿ�<br>
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


//��������
        JSON json = tRequest.postRequest();

//4���жϽ��״̬�����к�������
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {
            //5���̻����������ɹ�
            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");

        } else {
            //6���̻���������ʧ��
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>
    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>