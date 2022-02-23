<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.abc.pay.client.ebus.*" %>
<%@ page import="com.abc.pay.client.*" %>
<%@ page import="java.util.*" %>
<%@ page import="a.MerchantParaFromDB1" %>
<% response.setHeader("Cache-Control", "no-cache"); %>
<%
    //²ÎÊýË¢ÐÂ
    MerchantParaFactory factoryDB = new MerchantParaFromDB1();
    factoryDB.refreshConfig();

%>