<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.LinkedHashMap" %>
<%
    request.setCharacterEncoding("gb2312");
    response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>ũ������֧��ƽ̨-�̻��ӿڷ���-����ȷ������</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>����ȷ������<br>
    <%
        //1�����ɵ���ȷ���������
        GuanteePaySendMQOrderRequest tRequest = new GuanteePaySendMQOrderRequest();
        tRequest.dicRequest.put("OrderNo", request.getParameter("OriginalOrderNo")); //֧��������
        tRequest.dicRequest.put("NewOrderNo", request.getParameter("ConfirmedOrderNo")); //����ȷ�Ͻ��ױ��
        tRequest.dicRequest.put("OrderAmount", request.getParameter("OrderAmount")); //���׽��
        tRequest.dicRequest.put("AdvancedFund", request.getParameter("AdvancedFund")); //�Ƿ����

        //�����Ҫר�ߵ�ַ�����ô˷�����
        //tRequest.setConnectionFlag(true);

        String[] SubMerchantID_arr = new String[]{};
        String[] SplitAmount_arr = new String[]{};

        SubMerchantID_arr = request.getParameterValues("SplitMerchantID");
        SplitAmount_arr = request.getParameterValues("SplitAmount");

        LinkedHashMap map = null;

        if (SubMerchantID_arr != null) {
            for (int i = 0; i < SubMerchantID_arr.length; i++) {
                map = new LinkedHashMap();
                //map.put("SeqNo       ", String.valueOf(i + 1));
                map.put("SplitMerchantID", SubMerchantID_arr[i]);
                map.put("SplitAmount", SplitAmount_arr[i]);

                tRequest.dicSplitAccInfo.put(i + 1, map);
            }
        }

        //3����������ȡ�ý��
        JSON json = tRequest.postRequest();

        //4���ж��˿���״̬�����к�������
        StringBuilder strMessage = new StringBuilder("");
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        if (ReturnCode.equals("0000")) {
            //�ɹ�
            out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");

        } else {
            //ʧ��
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>
    <a href='Merchant.html'>���̻���ҳ</a></CENTER>
</BODY>
</HTML>