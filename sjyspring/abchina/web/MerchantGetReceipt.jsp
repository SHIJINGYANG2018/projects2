<%@ page language="java" import="java.util.*" pageEncoding="gb2312" %>
<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.*" %>
<% response.setHeader("Cache-Control", "no-cache"); %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

//1�����ɽ��ײ�ѯ����
    String subMerchantNo = request.getParameter("SubMerchantNo");
    String orderNo = request.getParameter("OrderNo");


    GetReceiptRequest tReceiptRequest = new GetReceiptRequest();
    tReceiptRequest.receiptRequest.put("SubMerchantNo", subMerchantNo);    //�趨��������
    tReceiptRequest.receiptRequest.put("OrderNo", orderNo);    //�趨������� ����Ҫ��Ϣ��

//�����Ҫר�ߵ�ַ�����ô˷�����
//tQueryRequest.setConnectionFlag(true);
    JSON json = tReceiptRequest.postRequest();
//JSON json = tQueryRequest.extendPostRequest(1);

    String ReturnCode = json.GetKeyValue("ReturnCode");
    String ErrorMessage = json.GetKeyValue("ErrorMessage");

    if (ReturnCode.equals("0000")) {
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
        //4����ȡ�����Ϣ
        String receiptStr = json.GetKeyValue("ImageCode");
        byte[] imageBytes = tReceiptRequest.decompressFromBase64String(receiptStr);

        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + orderNo + ".bmp");
        //ServletOutputStream outReceipt = response.getOutputStream();
        response.getOutputStream().write(imageBytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();

        //Ϊ��ֹ�׳�IllegalStateException,������������д���
        //Servlet�й涨response.getWriter()��response.getOutputStream()���ɵĶ��󣬲���ͬʱ����
        out.clear();
        out = pageContext.pushBody();
    } else {
        //5���̻��������ص��ӻص�ʧ��
        out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
        out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");
    }
%>

<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-���ӻص�����</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>���ӻص�����<br>

    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>
