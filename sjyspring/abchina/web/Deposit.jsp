<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.LinkedHashMap" %>
<%
    request.setCharacterEncoding("GBK");
    response.setHeader("Cache-Control", "no-cache");
%>
<%
    //1�����ɶ�������
    DepositRequest tPaymentRequest = new DepositRequest();
    tPaymentRequest.dicOrder.put("orderTimeoutDate", request.getParameter("orderTimeoutDate"));     //�趨������Ч��
    tPaymentRequest.dicOrder.put("CurrencyCode", request.getParameter("CurrencyCode"));             //�趨���ױ���
    tPaymentRequest.dicOrder.put("OrderAmount", request.getParameter("PaymentRequestAmount"));      //�趨���׽��
    tPaymentRequest.dicOrder.put("OrderDesc", request.getParameter("OrderDesc"));                   //�趨����˵��

//3������֧���������
    String paymentLinkType = request.getParameter("PaymentLinkType");
    tPaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //�趨֧�����뷽ʽ

    tPaymentRequest.dicRequest.put("NotifyType", request.getParameter("NotifyType"));              //�趨֪ͨ��ʽ
    tPaymentRequest.dicRequest.put("ResultNotifyURL", request.getParameter("ResultNotifyURL"));    //�趨֪ͨURL��ַ
    tPaymentRequest.dicRequest.put("MerchantRemarks", request.getParameter("MerchantRemarks"));    //�趨����
    tPaymentRequest.dicRequest.put("ReceiveSubMerchantNo", request.getParameter("ReceiveSubMerchantNo")); //�趨�տ����̻���


    JSON json = tPaymentRequest.postRequest();
//JSON json = tPaymentRequest.extendPostRequest(1);

    String ReturnCode = json.GetKeyValue("ReturnCode");
    String ErrorMessage = json.GetKeyValue("ErrorMessage");
    if (ReturnCode.equals("0000")) {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
        //response.sendRedirect(json.GetKeyValue("PaymentURL"));
    } else {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
//tPaymentRequest.extendPostRequest(1);
%>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-��ֵ����</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>��ֵ����<br>
    <%
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>

    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>