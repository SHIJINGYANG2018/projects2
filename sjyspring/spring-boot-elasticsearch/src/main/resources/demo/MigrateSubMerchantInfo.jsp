<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-�����̻�Ǩ�ƽӿ�</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>�����̻�Ǩ�ƽӿ�<br>
    <%
        //1��ȡ�ö����̻�Ǩ������Ҫ����Ϣ
        String fromMerchantNo = request.getParameter("FromMerchantNo");
        String subMerchantNo = request.getParameter("SubMerchantNo");

//2�������������
        MigrateSubMerchantInfoRequest tRequest = new MigrateSubMerchantInfoRequest();
        tRequest.dicRequest.put("FromMerchantNo", fromMerchantNo);
        tRequest.dicRequest.put("SubMerchantNo", subMerchantNo);

//3���ύ����
        JSON json = tRequest.postRequest();

//4���жϽ��״̬�����к�������
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {
            //5����ɹ�
            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");

        } else {
            //6������ʧ��
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");

        }
    %>
    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>