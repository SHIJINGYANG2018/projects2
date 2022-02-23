//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc.pay.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;

public abstract class TrxRequest {
    private LogWriter iLogWriter = null;
    private String iECMerchantType = "";
    public static final String EC_MERCHANT_TYPE_EBUS = "EBUS";
    public boolean connectionFlag = false;
    private MerchantPara para = null;
    protected static final String CHARSETCODE_UTF_8 = "UTF-8";
    private String tRequestMesg = "";

    public TrxRequest(String aECMerchantType) {
        this.iECMerchantType = aECMerchantType;

        try {
            this.para = MerchantConfig.getUniqueInstance().getPara();
        } catch (TrxException var3) {
            System.out.println("获取参数异常:" + var3.getMessage());
        }

    }

    public JSON postRequest() {
        return this.extendPostRequest(1);
    }

    public JSON extendPostRequest(int i) {
        String tResponseMessage = "";
        JSON json = null;
        SignService sign = null;

        try {
            this.iLogWriter = new LogWriter();
            this.iLogWriter.logNewLine("TrustPayClient Java V3.1.7 交易开始==========================");
            this.iLogWriter.logNewLine("检查交易请求是否合法：");
            this.checkRequest();
            this.iLogWriter.logNewLine("正确");
            this.iLogWriter.logNewLine("交易报文：");
            String tRequestMessage = this.getRequestMessage();
            this.iLogWriter.logNewLine("完整交易报文：");
            tRequestMessage = this.composeRequestMessage(i, tRequestMessage);
            this.iLogWriter.logNewLine("签名后的报文：");
            sign = SignService.getUniqueInstance();
            tRequestMessage = sign.signMessage(i, tRequestMessage);
            this.iLogWriter.logNewLine("发送交易报文至网上支付平台：");
            tResponseMessage = this.sendMessage(tRequestMessage);
            this.iLogWriter.logNewLine("接收报文：");
            this.iLogWriter.logNewLine(tResponseMessage.toString());
            tResponseMessage = tResponseMessage.substring(tResponseMessage.indexOf("{\"MSG"), tResponseMessage.length());
            json = new JSON(tResponseMessage);
            this.iLogWriter.logNewLine("验证网上支付平台响应报文的签名：");
            SignService.verifySign(json, tResponseMessage);
            this.iLogWriter.log("正确");
            this.iLogWriter.logNewLine("生成交易响应对象：");
            this.iLogWriter.logNewLine("交易结果：[" + json.GetKeyValue("ReturnCode") + "]");
            this.iLogWriter.logNewLine("错误信息：[" + json.GetKeyValue("ErrorMessage") + "]");
        } catch (TrxException var16) {
            if (this.iLogWriter != null) {
                this.iLogWriter.logNewLine("错误代码：[" + var16.getCode() + "]    错误信息：[" + var16.getDetailMessage() + "]");
            }

            tResponseMessage = "{\"ReturnCode\":\"" + var16.getCode() + "\";\"ErrorMessage\":\"" + var16.getDetailMessage() + "\"}";
            json = new JSON(tResponseMessage);
            json.setJsonString(tResponseMessage);
        } catch (Exception var17) {
            if (this.iLogWriter != null) {
                this.iLogWriter.logNewLine("错误代码：[系统发生无法预期的错误]    错误信息：[" + var17.getMessage() + "]");
            }

            tResponseMessage = "{\"ReturnCode\":\"1999\";\"ErrorMessage\":\"系统发生无法预期的错误 - " + var17.getMessage() + "\"}";
            json = new JSON(tResponseMessage);
            json.setJsonString(tResponseMessage);
        } finally {
            if (this.iLogWriter != null) {
                this.iLogWriter.logNewLine("交易结束==================================================");

                try {
                    this.iLogWriter.closeWriter(MerchantConfig.getTrxLogFile());
                } catch (Exception var15) {
                    var15.printStackTrace();
                }
            }

        }

        return json;
    }

    public String genSignature(int i) throws TrxException {
        String tResponseMessage = "";
        SignService sign = null;

        try {
            this.iLogWriter = new LogWriter();
            this.iLogWriter.logNewLine("TrustPayClient Java V3.1.7 交易开始==========================");
            this.iLogWriter.logNewLine("检查交易请求是否合法：");
            this.checkRequest();
            this.iLogWriter.logNewLine("正确");
            this.iLogWriter.logNewLine("交易报文：");
            String tRequestMessage = this.getRequestMessage();
            this.iLogWriter.logNewLine(tRequestMessage.toString());
            this.iLogWriter.logNewLine("完整交易报文：");
            tRequestMessage = this.composeRequestMessage(i, tRequestMessage);
            this.iLogWriter.logNewLine(tRequestMessage.toString());
            this.iLogWriter.logNewLine("签名后的报文：");
            sign = SignService.getUniqueInstance();
            tRequestMessage = sign.signMessage(i, tRequestMessage);
            StringBuffer tTempStringBuffer = (new StringBuffer("")).append(tRequestMessage.toString());
            this.tRequestMesg = tTempStringBuffer.toString();
            this.iLogWriter.logNewLine(this.tRequestMesg);
            this.tRequestMesg = this.tRequestMesg.replace("\"", "&quot;");
            this.iLogWriter.logNewLine("提交网上支付平台的报文：\n" + this.tRequestMesg);
        } catch (TrxException var14) {
            if (this.iLogWriter != null) {
                this.iLogWriter.logNewLine("错误代码：[" + var14.getCode() + "]    错误信息：[" + var14.getMessage() + " - " + var14.getDetailMessage() + "]");
            }

            throw new TrxException(var14.getCode(), var14.getMessage() + " - " + var14.getDetailMessage());
        } catch (Exception var15) {
            if (this.iLogWriter != null) {
                this.iLogWriter.logNewLine("错误代码：[1999]    错误信息：[系统发生无法预期的错误 - " + var15.getMessage() + "]");
            }

            throw new TrxException("1999", "系统发生无法预期的错误 - " + var15.getMessage());
        } finally {
            if (this.iLogWriter != null) {
                this.iLogWriter.logNewLine("交易结束==================================================");

                try {
                    this.iLogWriter.closeWriter(MerchantConfig.getTrxLogFile());
                } catch (Exception var13) {
                }
            }

        }

        return this.tRequestMesg;
    }

    protected abstract void checkRequest() throws TrxException;

    protected abstract String getRequestMessage() throws TrxException;

    private String composeRequestMessage(int i, String aMessage) throws TrxException {
        String tMessage = "{\"Version\":\"V3.0.0\",\"Format\":\"JSON\",\"Merchant\":{\"ECMerchantType\":\"" + this.iECMerchantType + "\",\"MerchantID\":\"" + this.para.getMerchantID(i) + "\"}," + "\"TrxRequest\":{" + aMessage.substring(1, aMessage.length()) + "}}";
        return tMessage.toString();
    }

    private String sendMessage(String aMessage) throws TrxException {
        String tMessage = aMessage;
        this.iLogWriter.logNewLine("提交网上支付平台的报文：\n" + aMessage);
        boolean var3 = false;

        int tContentLength;
        try {
            tContentLength = tMessage.getBytes("UTF-8").length;
            this.iLogWriter.logNewLine("报文长度：" + tContentLength);
            if (tContentLength > 8000) {
                throw new TrxException("1101", "商户提交的交易资料不合法", "报文长度超过8000Bytes");
            }
        } catch (TrxException var81) {
            throw var81;
        } catch (Exception var82) {
            throw new TrxException("1999", "系统发生无法预期的错误", var82.getMessage());
        }

        String tResponseMessage = "";
        Socket tSocket = null;
        PrintWriter tOut = null;
        BufferedReader tIn = null;
        String connectMethod = "";
        String url = "";
        int port = 0;
        String tProxyIP = this.para.getProxyIP();
        String tProxyPort = this.para.getProxyPort();
        String tTrustPayServerTimeout = this.para.getTrustPayServerTimeout();
        int tTrustPayServerTimeoutInt = 0;
        PostMethod post = null;

        SSLSocket tSSLSocket;
        try {
            if (!this.connectionFlag) {
                connectMethod = this.para.getTrustPayConnectMethod();
                url = this.para.getTrustPayServerName();
                port = Integer.valueOf(this.para.getTrustPayServerPort());
            } else {
                connectMethod = this.para.getTrustPayConnectMethodLine();
                url = this.para.getTrustPayServerNameLine();
                port = Integer.valueOf(this.para.getTrustPayServerPortLine());
            }

            this.iLogWriter.logNewLine("连线网上支付平台：");
            if (connectMethod.equals("https")) {
                MerchantConfig.getUniqueInstance();
                SSLSocketFactory ssf = MerchantConfig.getSSLSocketFactory();
                tSSLSocket = (SSLSocket) ssf.createSocket(InetAddress.getByName(url), port);
                tSSLSocket.startHandshake();
                tSocket = tSSLSocket;
            } else {
                tSocket = new Socket(InetAddress.getByName(url), port);
            }
        } catch (Exception var68) {
            this.iLogWriter.logNewLine(var68.getMessage());
            throw new TrxException("1201", "无法连线网上支付平台", "无法建立socket连接");
        }

        try {
            this.iLogWriter.log("成功");
            if ((tProxyIP.equals("") || tProxyPort.equals("")) && tTrustPayServerTimeout.equals("")) {
                this.iLogWriter.logNewLine("提交交易报文：");
                StringBuffer tHttpRequest = (new StringBuffer("")).append("POST ").append(this.para.getTrustPayTrxURL()).append(" HTTP/1.1").append("\r\n").append("User-Agent: Java/1.3.1").append("\r\n").append("Host: ").append(url).append("\r\n").append("Accept: text/html, image/gif, image/jpeg, *; q=.2, * /*; q=.2").append("\r\n").append("Connection: keep-alive").append("\r\n").append("Content-Type: application/x-www-form-urlencoded").append("\r\n").append("Content-Length: ").append(tContentLength).append("\r\n").append("\r\n").append(tMessage);
                tOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(((Socket) tSocket).getOutputStream(), "UTF-8")));
                tOut.write(tHttpRequest.toString());
                tOut.println();
                tOut.flush();
                this.iLogWriter.log("成功");
                this.iLogWriter.logNewLine("等待网上支付平台返回交易结果：");
                tIn = new BufferedReader(new InputStreamReader(((Socket) tSocket).getInputStream(), "GBK"));
                tSSLSocket = null;

                String tLine;
                while ((tLine = tIn.readLine()) != null) {
                    tResponseMessage = tResponseMessage + tLine;
                    if (tLine.trim().startsWith("{\"MSG")) {
                        break;
                    }
                }

                this.iLogWriter.log("成功");
                this.iLogWriter.logNewLine("返回报文：");
                this.iLogWriter.log("\n" + tResponseMessage.toString());
                if (tResponseMessage.indexOf("HTTP/1.1 200") == -1) {
                    throw new TrxException("1206", "网上支付平台服务暂时停止");
                }

                if (tResponseMessage == null) {
                    throw new TrxException("1205", "无法辨识网上支付平台的响应报文", "无[MSG]段！");
                }
            } else {
                if (443 == port) {
                    url = connectMethod + "://" + this.para.getTrustPayServerName() + this.para.getTrustPayTrxURL();
                } else {
                    url = connectMethod + "://" + this.para.getTrustPayServerName() + ":" + port + this.para.getTrustPayTrxURL();
                }

                try {
                    HttpClient client = new HttpClient();
                    if (!tTrustPayServerTimeout.equals("")) {
                        tTrustPayServerTimeoutInt = Integer.valueOf(this.para.getTrustPayServerTimeout());
                        client.getHttpConnectionManager().getParams().setSoTimeout(tTrustPayServerTimeoutInt);
                        client.getHttpConnectionManager().getParams().setConnectionTimeout(tTrustPayServerTimeoutInt);
                    }

                    if (!tProxyIP.equals("") && !tProxyPort.equals("")) {
                        client.getHostConfiguration().setProxy(tProxyIP, Integer.valueOf(tProxyPort));
                    }

                    client.getHttpConnectionManager().getParams().setTcpNoDelay(true);
                    client.getHttpConnectionManager().getParams().setStaleCheckingEnabled(false);
                    this.iLogWriter.logNewLine("url:" + url);

                    try {
                        post = new PostMethod(url);
                    } catch (Exception var67) {
                        var67.printStackTrace();
                    }

                    post.getParams().setCookiePolicy("ignoreCookies");
                    this.iLogWriter.logNewLine("HTTPClient发送交易请求报文开始.");
                    byte[] tXMLMessageBytes = tMessage.getBytes("UTF-8");
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(tXMLMessageBytes);
                    post.setRequestEntity(new InputStreamRequestEntity(byteArrayInputStream));
                    post.setRequestHeader("Content-type", "text/xml; charset=UTF-8");
                    int result = client.executeMethod(post);
                    this.iLogWriter.logNewLine("HTTPClient请求报文发送成功");
                    this.iLogWriter.logNewLine("HTTPClient等待网上支付平台返回交易结果开始");
                    tResponseMessage = new String(post.getResponseBody(), post.getResponseCharSet());
                    this.iLogWriter.logNewLine("HTTPClient取网上支付平台返回交易结果完成:[" + result + "]");
                    if (result < 200 || result >= 400) {
                        throw new TrxException("1206", "网上支付平台服务暂时停止");
                    }
                } catch (TrxException var69) {
                    this.iLogWriter.logNewLine("<====HTTPClient发送交易报文失败:" + var69.getMessage() + "====>");
                    throw var69;
                } catch (UnknownHostException var70) {
                    this.iLogWriter.logNewLine("<====HTTPClient发送交易报文失败:" + var70.getMessage() + "====>");
                    throw new TrxException("1201", "无法连线网上支付平台", "无法取得[" + this.para.getTrustPayServerName() + "]的IP地址!");
                } catch (ConnectException var71) {
                    this.iLogWriter.logNewLine("<====HTTPClient发送交易报文失败:" + var71.getMessage() + "====>");
                    throw new TrxException("1201", "无法连线网上支付平台", "无法连线" + this.para.getTrustPayServerName() + "的" + this.para.getTrustPayServerPort() + "端口!");
                } catch (SocketException var72) {
                    this.iLogWriter.logNewLine("<====HTTPClient发送交易报文失败:" + var72.getMessage() + "====>");
                    throw new TrxException("1202", "提交交易时发生网络错误", "连线中断！");
                } catch (IOException var73) {
                    this.iLogWriter.logNewLine("<====HTTPClient发送交易报文失败:" + var73.getMessage() + "====>");
                    throw new TrxException("1202", "提交交易时发生网络错误", "连线中断！");
                } finally {
                    post.releaseConnection();
                }
            }
        } catch (TrxException var75) {
            throw var75;
        } catch (UnknownHostException var76) {
            var76.printStackTrace(System.out);
            throw new TrxException("1201", "无法连线网上支付平台", "无法取得[" + url + "]的IP地址!");
        } catch (ConnectException var77) {
            var77.printStackTrace(System.out);
            throw new TrxException("1201", "无法连线网上支付平台", "无法连线" + url + "的" + port + "端口!");
        } catch (SocketException var78) {
            var78.printStackTrace(System.out);
            throw new TrxException("1202", "提交交易时发生网络错误", "连线中断SocketException！");
        } catch (IOException var79) {
            var79.printStackTrace(System.out);
            throw new TrxException("1202", "提交交易时发生网络错误", "连线中断IOException！");
        } finally {
            if (tIn != null) {
                try {
                    tIn.close();
                } catch (Exception var66) {
                }
            }

            if (tOut != null) {
                try {
                    tOut.close();
                } catch (Exception var65) {
                }
            }

            if (tSocket != null) {
                try {
                    ((Socket) tSocket).close();
                } catch (Exception var64) {
                }
            }

        }

        return tResponseMessage;
    }
}
