package com.sjy.websocketnetty.chatbotTest;



import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class RcsMobileHttpUtil {

    public static String httpRcsMessagePost(String url, RcsSubmitMessage message, String authorization, String date) throws Exception {

        /** #####################初始配置信息################################ */
        String msg = getMessage(message);
        return   httpsPost(url, msg, authorization, message.getDesAddress(), date, message);

    }
    private static String httpsPost(String url, String msg, String token,String desmobile, String date,RcsSubmitMessage message) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder response = new StringBuilder();
        /** #####################初始配置信息################################ */
        /** 调用post方法 */
        try {
            URL realUrl = new URL(url);
            BaseUtils.trustAllHttpsCertificates();
            HostnameVerifier hv = (urlHostName, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/xml");
            //conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("connection", "Keep-Alive");
            token = token.replaceAll("\r|\n", "");
            conn.setRequestProperty("Authorization", "Basic "+token);
            conn.setRequestProperty("Date", date);
            conn.setRequestProperty("Address", "+86"+desmobile);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            out.print(msg);
            out.flush();
            out.close();
            if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
                InputStream inputStr = conn.getInputStream();
                in = new BufferedReader(
                        new InputStreamReader(inputStr));
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                message.setSubmitStatus("0");
            }else {
                InputStream inputStr = conn.getErrorStream();
                in = new BufferedReader(
                        new InputStreamReader(inputStr));
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                message.setSubmitStatus("1");
            }
            ((HttpURLConnection)conn).disconnect();
            return response.toString();
        } catch (MalformedURLException e) {
            response = new StringBuilder("-1");
            System.out.println(("MalformedURLException"+e));
        } catch (IOException e) {
            response = new StringBuilder("-1");
            System.out.println("IOException"+e);
        }
        return response.toString();
    }

    private static String getMessage(RcsSubmitMessage message){
        StringBuilder sb  = new StringBuilder();
        String inReplyToContributionID = message.getInReplyToContributionId();
        String desAddress = message.getDesAddress();
        String fallbackContentType =null;
        if (message.getFallbackSupported()) {

            fallbackContentType = MobileFallbackContentTypeEnum.getName(message.getFallbackContentType());
        }
        String contentType = MobileContentTypeEnum.getName(message.getContentType());
        String chatbotNumber = message.getChatbotNumber();
        String senderAddress="sip:"+ chatbotNumber +"@botplatform.rcs.chinamobile.com";
        String content = message.getContent();
        if ("".equals(inReplyToContributionID) || inReplyToContributionID == null) {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<msg:outboundMessageRequest xmlns:msg=\"urn:oma:xml:rest:netapi:messaging:1\">" +
                    "<address>tel:+86"+ desAddress +"</address>" +
                    "<destinationAddress>tel:+86"+desAddress+"</destinationAddress>" +
                    "<senderAddress>"+senderAddress+"</senderAddress>" +
                    "<senderName>摩森特（北京）科技有限公司</senderName>" +
                    "<outboundIMMessage>" +
                    "<subject>hello from the rest of us!</subject>" +
                    "<imFormat>IMPagerMode</imFormat>" +
                    "<conversationID>"+ UUID.randomUUID().toString()+"</conversationID>" +
                    "<contributionID>"+UUID.randomUUID().toString()+"</contributionID>" +
                    "<messageId>"+UUID.randomUUID().toString()+"</messageId>" +
                    "<reportRequest>Delivered</reportRequest>" +
                    "<reportRequest>Failed</reportRequest>");
            if (message.getShortMessageSupported()) {
                sb.append("<shortMessageSupported>true</shortMessageSupported>");
                sb.append("<smBodyText>").append(message.getSmsContent()).append("</smBodyText>");
            }
            if (message.getFallbackSupported()) {
                sb.append("<fallbackSupported>true</fallbackSupported>");
                sb.append("<fallbackContentType>").append(fallbackContentType).append("</fallbackContentType>");
                sb.append("<rcsBodyText>").append(message.getFallbackRcsBodyText()).append("</rcsBodyText>");
            }
            sb.append("<serviceCapability>" +
                    "<capabilityId>ChatbotSA</capabilityId>" +
                    "<version>+g.gsma.rcs.botversion=&quot;#&lt;=1&quot;</version>" +
                    "</serviceCapability>" +
                    "<contentType>"+contentType+"</contentType>" +
                    "<bodyText>"+ content +"</bodyText>" +
                    "</outboundIMMessage>" +
                    "<clientCorrelator>"+ chatbotNumber +"</clientCorrelator>" +
                    "</msg:outboundMessageRequest>");
        }else {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<msg:outboundMessageRequest xmlns:msg=\"urn:oma:xml:rest:netapi:messaging:1\">" +
                    "<address>tel:+86"+desAddress+"</address>" +
                    "<destinationAddress>tel:+86"+desAddress+"</destinationAddress>" +
                    "<inReplyToContributionID>"+inReplyToContributionID+"</inReplyToContributionID>"+
                    "<senderAddress>"+senderAddress+"</senderAddress>" +
                    "<senderName>北京国都</senderName>" +
                    "<outboundIMMessage>" +
                    "<subject>hello from the rest of us!</subject>" +
                    "<imFormat>IMPagerMode</imFormat>" +
                    "<conversationID>"+UUID.randomUUID().toString()+"</conversationID>" +
                    "<contributionID>"+UUID.randomUUID().toString()+"</contributionID>" +
                    "<messageId>"+UUID.randomUUID().toString()+"</messageId>" +
                    "<reportRequest>Delivered</reportRequest>" +
                    "<reportRequest>Failed</reportRequest>");
            if (message.getShortMessageSupported()) {
                sb.append("<shortMessageSupported>true</shortMessageSupported>");
                sb.append("<smBodyText>"+message.getSmsContent()+"</smBodyText>");
            }
            if (message.getFallbackSupported()) {
                sb.append("<fallbackSupported>true</fallbackSupported>");
                sb.append("<fallbackContentType>"+fallbackContentType+"</fallbackContentType>");
                sb.append("<rcsBodyText>"+message.getFallbackRcsBodyText()+"</rcsBodyText>");
            }
            sb.append("<serviceCapability>" +
                    "<capabilityId>ChatbotSA</capabilityId>" +
                    "<version>+g.gsma.rcs.botversion=&quot;#=1&quot;</version>" +
                    "</serviceCapability>" +
                    "<contentType>"+contentType+"</contentType>" +
                    "<bodyText>"+ content +"</bodyText>" +
                    "</outboundIMMessage>" +
                    "<clientCorrelator>"+ chatbotNumber +"</clientCorrelator>" +
                    "</msg:outboundMessageRequest>");
        }


        System.out.println(sb.toString());
        return sb.toString();
    }


}
