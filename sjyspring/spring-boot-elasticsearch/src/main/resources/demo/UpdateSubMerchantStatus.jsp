<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-�޸Ķ����̻�״̬</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>�޸Ķ����̻�״̬<br>
    <%
        //request.setCharacterEncoding("GBK");

//NumberFormat tFormat = NumberFormat.getInstance();
//tFormat.setMaximumFractionDigits(2);
//tFormat.setGroupingUsed(false);
//tFormat.setMinimumFractionDigits(2);

//1��ȡ���޸Ķ����̻�״̬����Ҫ����Ϣ
        String subMerId = request.getParameter("SubMerId");
        String updateFlag = request.getParameter("UpdateFlag");


//2���޸Ķ����̻�״̬�������
        UpdateSubMerchantStatusRequest tRequest = new UpdateSubMerchantStatusRequest();
        tRequest.dicRequest.put("SubMerId", subMerId);
        tRequest.dicRequest.put("UpdateFlag", updateFlag);


//3����������
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