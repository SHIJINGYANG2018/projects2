package com.sjy.abcpay.merchant;

import java.util.ArrayList;

import com.abc.pay.client.CertHelper;
import com.abc.pay.client.FileUtil;
import com.abc.pay.client.MerchantPara;
import com.abc.pay.client.MerchantParaFactory;
import com.abc.pay.client.TrxException;

/**
 * ���幤��-���˹����������ݿ��ж�ȡ����
 * �������ֲ�Ʒ��MerchantParaWeb��
 */
public class MerchantParaFromDB1 extends MerchantParaFactory {

    /**
     * �̻��������ļ���Դ����
     */
    private static MerchantPara paraWeb = null;

    /**
     * ��ʼ���
     */
    private static boolean iIsInitialedWeb = false;

    @Override
    public void refreshConfig() throws TrxException {
        iIsInitialedWeb = false;
    }

    public void init(MerchantPara para) {
        try {
            //##����֧��ƽ̨ϵͳ���ö� - �������� - ������� 
            //#����֧��ƽ̨ͨѶ��ʽ��http / https��
            //����
            para.setTrustPayConnectMethod("https");
            //ר��
            para.setTrustPayConnectMethodLine("https");

            //#����֧��ƽ̨��������
            //����
            para.setTrustPayServerName("pay.abchina.com");
            //ר��
            para.setTrustPayServerNameLine("pay.abchina.com");

            //#����֧��ƽ̨���׶˿�
            //����
            para.setTrustPayServerPort("443");
            //ר��
            para.setTrustPayServerPortLine("443");

            //#����֧��ƽ̨������ַ
            para.setTrustPayTrxURL("/ebus/ReceiveMerchantTrxReqServlet");
            para.setTrustPayTrxIEURL("https://pay.abchina.com/ebus/ReceiveMerchantIERequestServlet");

            //#ҳ���ύ֧������ʧ�ܺ��ת���ַ
            para.setMerchantErrorURL("http://127.0.0.1:8080/ebusnewupdate/Merchant.html");

            //##����֧��ƽ̨ϵͳ���ö� - �������� - ����֤����·����ʹ��ͱ��ش��·����ƥ�䣨����·����
            //#����֧��ƽ̨֤��
            para.setTrustPayCertFileName("D:/mocentre/abc/TrustPay.cer");

            //#ũ�и�֤���ļ�
            para.setTrustStoreFileName("D:/mocentre/abc/abc.truststore");

            //#ũ�и�֤���ļ�����
            para.setTrustStorePassword("changeit");

            //�����̻���š�����Ƕ��̻�����iMerchantIDList���ö�����¼
            ArrayList<String> iMerchantIDList = new ArrayList<String>();
            iMerchantIDList.add("103881105990003");
            para.setMerchantIDList(iMerchantIDList);

            FileUtil util = new FileUtil();

            //�����̻�֤�顣����Ƕ��̻�����iMerchantCertNameList���ö�����¼��ע�⣺�̻�֤������˳��Ҫ���̻����˳��һ��
            ArrayList<byte[]> iMerchantCertList = new ArrayList<byte[]>();
            iMerchantCertList.add(util.readFile("D:/mocentre/abc/mocentre.pfx"));
            para.setMerchantCertFileList(iMerchantCertList);

            //�����̻�֤�����롣����Ƕ��̻�����iMerchantPasswordList���ö�����¼��ע�⣺����˳��Ҫ���̻����˳��һ��
            ArrayList<String> iMerchantPasswordList = new ArrayList<String>();
            iMerchantPasswordList.add("10000000");// �̻�˽Կ����
            para.setMerchantCertPasswordList(iMerchantPasswordList);

            //#������־�ļ����Ŀ¼
            para.setLogPath("D:/mocentre/abc/log");
            //#֤�鴢��ý��
            para.setMerchantKeyStoreType("0");

            //һ���̻���ѡ���ļ�֤��
            if (para.getMerchantKeyStoreType().equals(MerchantPara.KEY_STORE_TYPE_FILE)) {
                CertHelper.bindMerchantCertificate(para, iMerchantCertList, iMerchantPasswordList);
            } else if (para.getMerchantKeyStoreType().equals(MerchantPara.KEY_STORE_TYPE_SIGN_SERVER)) {
            } else {
                throw new TrxException(TrxException.TRX_EXC_CODE_1001, TrxException.TRX_EXC_MSG_1001 + " - ֤�鴢��ý�����ô���");
            }

            //�趨��������
            para.setProxyIP("");
            para.setProxyPort("");

            //�趨���ӳ�ʱʱ��
            para.setTrustPayServerTimeout("");

            //#Sign Server��ַ����KeyStoreType=1ʱ�������趨��
            //para.setSignServerIP("");
            //#Sign Server�˿ڣ���KeyStoreType=1ʱ�������趨��
            //para.setSignServerPort("");
            //#Sign Server���루��KeyStoreType=1ʱ��ѡ���趨��
            //para.setSignServerPassword("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //iIsInitialed = true;
        System.out.println("[Trustpay�̻���API] - ��ʼ - ���====================");
    }

    /**
     * MerchantParaFromDB�����ʵ��getMerchantPara����������MerchantPara���Ͷ���
     * �����ݿ��ж�ȡ�����
     * Ĭ��ʵ��Ϊweb
     */
    @Override
    public MerchantPara getMerchantPara() throws TrxException {
        if (!iIsInitialedWeb) {
            try {
                paraWeb = ParaClassWeb.getUniqueInstance();
            } catch (TrxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            init(paraWeb);
            iIsInitialedWeb = true;
        }
        return paraWeb;
    }

}