//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc.pay.client;

import com.abc.pay.client.*;
import com.sjy.abcpay.bean.PayConfig;
import com.sun.net.ssl.KeyManager;
import com.sun.net.ssl.SSLContext;
import com.sun.net.ssl.TrustManager;
import com.sun.net.ssl.TrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.KeyStore;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.ResourceBundle;

@AutoConfigurationPackage
@Configuration
@ConditionalOnWebApplication
public class MerchantConfig {
    @Value("${pay.abc.log.path}")
    private String logPath = "D:/mocentre/abc/log";
    @Value("${pay.abc.error.url}")
    private String errotUrl = "http://localhost:8223/aaa";

    private static ResourceBundle iResourceBundle = null;
    private static MerchantPara iPara = null;
    private static SSLSocketFactory iSSLSocketFactory = null;
    private static com.abc.pay.client.MerchantConfig uniqueInstanceOf_MerchantConfig = null;
    private static PayConfig payConfig = null;

    public static PayConfig getPayConfig() {
        return payConfig;
    }

    public static void setPayConfig(PayConfig payConfig) {
        MerchantConfig.payConfig = payConfig;
    }

    private MerchantConfig() throws TrxException {
        bundle();
    }

    private static synchronized void syncInit() throws TrxException {
        if (uniqueInstanceOf_MerchantConfig == null) {
            uniqueInstanceOf_MerchantConfig = new com.abc.pay.client.MerchantConfig();
        }
    }

    public static com.abc.pay.client.MerchantConfig getUniqueInstance() throws TrxException {
        if (uniqueInstanceOf_MerchantConfig == null) {
            syncInit();
        }

        return uniqueInstanceOf_MerchantConfig;
    }

    public MerchantPara getPara() {
        return iPara;
    }

    private void bundle() throws TrxException {
        //初始化abc支付配置
        iPara = MerchantParaWeb.getUniqueInstance();
        if (payConfig != null) {
            try {
                //##网上支付平台系统配置段 - 生产环境 - 请勿更改
                //#网上支付平台通讯方式（http / https）
                //公网
                iPara.setTrustPayConnectMethod("https");
                //专线
                iPara.setTrustPayConnectMethodLine("https");

                //#网上支付平台服务器名
                //公网
                iPara.setTrustPayServerName("pay.abchina.com");
                //专线
                iPara.setTrustPayServerNameLine("pay.abchina.com");

                //#网上支付平台交易端口
                //公网
                iPara.setTrustPayServerPort("443");
                //专线
                iPara.setTrustPayServerPortLine("443");

                //#网上支付平台交易网址
                iPara.setTrustPayTrxURL("/ebus/ReceiveMerchantTrxReqServlet");
                iPara.setTrustPayTrxIEURL("https://pay.abchina.com/ebus/ReceiveMerchantIERequestServlet");

                //#页面提交支付请求失败后的转向地址
                iPara.setMerchantErrorURL(errotUrl);

                //##网上支付平台系统配置段 - 生产环境 - 更改证书存放路径，使其和本地存放路径相匹配（绝对路径）
                //#网上支付平台证书
                iPara.setTrustPayCertFileName("D:/mocentre/abc/TrustPay.cer");

                //#农行根证书文件
                iPara.setTrustStoreFileName("D:/mocentre/abc/abc.truststore");

                //#农行根证书文件密码
                iPara.setTrustStorePassword("changeit");

                //设置商户编号。如果是多商户则在iMerchantIDList放置多条记录
                ArrayList<String> iMerchantIDList = new ArrayList<String>();
                iMerchantIDList.add(payConfig.getPartner());
                iPara.setMerchantIDList(iMerchantIDList);

                FileUtil util = new FileUtil();

                //设置商户证书。如果是多商户则在iMerchantCertNameList放置多条记录。注意：商户证书名称顺序要与商户编号顺序一致
                ArrayList<byte[]> iMerchantCertList = new ArrayList<byte[]>();
                iMerchantCertList.add(util.readFile(payConfig.getCacertPath()));
                iPara.setMerchantCertFileList(iMerchantCertList);

                //设置商户证书密码。如果是多商户则在iMerchantPasswordList放置多条记录。注意：密码顺序要与商户编号顺序一致
                ArrayList<String> iMerchantPasswordList = new ArrayList<String>();
                iMerchantPasswordList.add(payConfig.getSignKey());// 商户私钥密码
                iPara.setMerchantCertPasswordList(iMerchantPasswordList);

                //#交易日志文件存放目录
                iPara.setLogPath(logPath);
                //#证书储存媒体
                iPara.setMerchantKeyStoreType("0");

                //一般商户都选用文件证书
                if (iPara.getMerchantKeyStoreType().equals(MerchantPara.KEY_STORE_TYPE_FILE)) {
                    CertHelper.bindMerchantCertificate(iPara, iMerchantCertList, iMerchantPasswordList);
                } else if (iPara.getMerchantKeyStoreType().equals(MerchantPara.KEY_STORE_TYPE_SIGN_SERVER)) {
                } else {
                    throw new TrxException(TrxException.TRX_EXC_CODE_1001, TrxException.TRX_EXC_MSG_1001 + " - 证书储存媒体配置错误！");
                }

                //设定上网代理
                iPara.setProxyIP("");
                iPara.setProxyPort("");

                //设定连接超时时间
                iPara.setTrustPayServerTimeout("");

                //#Sign Server地址（当KeyStoreType=1时，必须设定）
                //para.setSignServerIP("");
                //#Sign Server端口（当KeyStoreType=1时，必须设定）
                //para.setSignServerPort("");
                //#Sign Server密码（当KeyStoreType=1时，选择设定）
                //para.setSignServerPassword("");
            } catch (Exception ex) {
                refreshConfig();
                ex.printStackTrace();
            }
            //iIsInitialed = true;
            System.out.println("[Trustpay商户端API] - 初始 - 完成====================");
            initSSL(iPara);
        } else {
            refreshConfig();
            throw new TrxException("9999", "没有abc支付配置  ", "PayConfig为空");
        }

    }

    private static void initSSL(MerchantPara para) throws TrxException {
        try {
            Provider tProvider = new com.sun.net.ssl.internal.ssl.Provider();
            SSLContext tSSLContext = SSLContext.getInstance("TLS", tProvider);
            TrustManagerFactory tTrustManagerFactory = TrustManagerFactory.getInstance("SunX509", tProvider);
            KeyStore tKeyStore = KeyStore.getInstance("JKS");
            String fileName = para.getTrustStoreFileName();
            if (fileName != null && !fileName.equals("")) {
                tKeyStore.load(new FileInputStream(fileName), para.getTrustStorePassword().toCharArray());
            } else {
                tKeyStore.load(new ByteArrayInputStream(para.getTrustStoreFile()), para.getTrustStorePassword().toCharArray());
            }

            tTrustManagerFactory.init(tKeyStore);
            TrustManager[] tTrustManager = tTrustManagerFactory.getTrustManagers();
            tSSLContext.init((KeyManager[]) null, tTrustManager, (SecureRandom) null);
            iSSLSocketFactory = tSSLContext.getSocketFactory();
        } catch (Exception var7) {
            System.out.println("[Trustpay商户端API] - 初始 - 系统发生无法预期的错误" + var7.getMessage());
            throw new TrxException("1999", "系统发生无法预期的错误", var7.getMessage());
        } catch (Error var8) {
            System.out.println("[Trustpay商户端API] - 初始 - 系统发生无法预期的错误" + var8.getMessage());
            throw new TrxException("1999", "系统发生无法预期的错误", var8.getMessage());
        }

        System.out.println("[Trustpay商户端API] - 初始 - SSLSocketFactory完成");
    }

    public static BufferedWriter getTrxLogFile() throws TrxException {
        return getTrxLogFile("TrxLog");
    }

    public static BufferedWriter getTrxLogFile(String aFileName) throws TrxException {
        BufferedWriter tLogFile = null;
        String tFileName = "";

        try {
            HiCalendar tHiCalendar = new HiCalendar();
            tFileName = iPara.getLogPath() + System.getProperty("file.separator") + aFileName + tHiCalendar.toString(".%Y%m%d.log");
            tLogFile = new BufferedWriter(new FileWriter(tFileName, true));
            return tLogFile;
        } catch (IOException var4) {
            throw new TrxException("1004", "无法写入交易日志文档", " - 系统无法写入交易日志至[" + tFileName + "]中!");
        }
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        return iSSLSocketFactory;
    }

    public static void refreshConfig() {
        uniqueInstanceOf_MerchantConfig = null;
    }
}
