<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.text.NumberFormat" %>

<% response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-����ȷ�ϲ�ѯ�ӿ�</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>����ȷ�ϲ�ѯ�ӿ�<br>
    <%
        //request.setCharacterEncoding("GBK");

//NumberFormat tFormat = NumberFormat.getInstance();
//tFormat.setMaximumFractionDigits(2);
//tFormat.setGroupingUsed(false);
//tFormat.setMinimumFractionDigits(2);

        String orderNo = request.getParameter("OrderNo");
        String orignalOrderNo = request.getParameter("OrignalOrderNo");
        String subMerchantNo = request.getParameter("SubMerchantNo");


        QueryPlatformConfirmRequest tRequest = new QueryPlatformConfirmRequest();
        tRequest.dicRequest.put("OrderNo", orderNo);
        tRequest.dicRequest.put("OrignalOrderNo", orignalOrderNo);
        tRequest.dicRequest.put("SubMerchantNo", subMerchantNo);


//��������
        JSON json = tRequest.postRequest();

//4���жϽ��״̬�����к�������
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if (ReturnCode.equals("0000")) {
            //�ɹ�
            out.println("ReturnCode      = [" + json.GetKeyValue("ReturnCode") + "]<br/>");
            out.println("ErrorMessage      = [" + json.GetKeyValue("ErrorMessage") + "]<br/>");
            out.println("TrxType      = [" + json.GetKeyValue("TrxType") + "]<br/>");
            out.println("ResultCode      = [" + json.GetKeyValue("ResultCode") + "]<br/>");
            out.println("ResultMessage      = [" + json.GetKeyValue("ResultMessage") + "]<br/>");

        } else {
            //ʧ��
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>
    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>