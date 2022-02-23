<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.LinkedHashMap" %>
<%
    request.setCharacterEncoding("gb2312");
    response.setHeader("Cache-Control", "no-cache"); %>
<HTML>
<HEAD><TITLE>农行网上支付平台-商户接口范例-担保确认请求</TITLE></HEAD>
<BODY BGCOLOR='#FFFFFF' TEXT='#000000' LINK='#0000FF' VLINK='#0000FF' ALINK='#FF0000'>
<CENTER>担保确认请求<br>
    <%
        //1、生成担保确认请求对象
        GuanteePaySendMQOrderRequest tRequest = new GuanteePaySendMQOrderRequest();
        tRequest.dicRequest.put("OrderNo", request.getParameter("OriginalOrderNo")); //支付订单号
        tRequest.dicRequest.put("NewOrderNo", request.getParameter("ConfirmedOrderNo")); //担保确认交易编号
        tRequest.dicRequest.put("OrderAmount", request.getParameter("OrderAmount")); //交易金额
        tRequest.dicRequest.put("AdvancedFund", request.getParameter("AdvancedFund")); //是否垫资

        //如果需要专线地址，调用此方法：
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

        //3、传送请求并取得结果
        JSON json = tRequest.postRequest();

        //4、判断退款结果状态，进行后续操作
        StringBuilder strMessage = new StringBuilder("");
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");
        if (ReturnCode.equals("0000")) {
            //成功
            out.println("ReturnCode   = [" + ReturnCode + "]<br/>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br/>");

        } else {
            //失败
            out.println("ReturnCode   = [" + ReturnCode + "]<br>");
            out.println("ErrorMessage = [" + ErrorMessage + "]<br>");
        }
    %>
    <a href='Merchant.html'>回商户首页</a></CENTER>
</BODY>
</HTML>