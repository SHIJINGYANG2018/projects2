
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.abc.pay.client.CertHelper;
import com.abc.pay.client.Constants;
import com.abc.pay.client.FileUtil;
import com.abc.pay.client.MerchantPara;
import com.abc.pay.client.MerchantParaFactory;
import com.abc.pay.client.MerchantParaWeb;
import com.abc.pay.client.TrxException;

/**
 * ���幤��-���˹����������ݿ��ж�ȡ����
 * �������ֲ�Ʒ��MerchantParaWeb��
 */
public class MerchantParaFromDB extends MerchantParaFactory {

    /**
     * �̻��������ļ���Դ����
     */
    private static MerchantPara paraWeb = null;

    /**
     * ��ʼ���
     */
    private static boolean iIsInitialedWeb = false;

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
            para.setTrustPayCertFileName("D:/cert/TrustPay.cer");

            //#ũ�и�֤���ļ�
            para.setTrustStoreFileName("D:/cert/abc.truststore");

            //#ũ�и�֤���ļ�����
            para.setTrustStorePassword("changeit");

            //�����̻���š�����Ƕ��̻�����iMerchantIDList���ö�����¼
            ArrayList<String> iMerchantIDList = new ArrayList<String>();
            iMerchantIDList.add("103881104410001");
            para.setMerchantIDList(iMerchantIDList);

            FileUtil util = new FileUtil();

            //�����̻�֤�顣����Ƕ��̻�����iMerchantCertNameList���ö�����¼��ע�⣺�̻�֤������˳��Ҫ���̻����˳��һ��
            ArrayList<byte[]> iMerchantCertList = new ArrayList<byte[]>();
            iMerchantCertList.add(util.readFile("D:/cert/103881104410001.pfx"));
            para.setMerchantCertFileList(iMerchantCertList);

            //�����̻�֤�����롣����Ƕ��̻�����iMerchantPasswordList���ö�����¼��ע�⣺����˳��Ҫ���̻����˳��һ��
            ArrayList<String> iMerchantPasswordList = new ArrayList<String>();
            iMerchantPasswordList.add("11111111");// �̻�˽Կ����
            para.setMerchantCertPasswordList(iMerchantPasswordList);

            //#������־�ļ����Ŀ¼
            para.setLogPath("D:/workspace/ebusnewupdate/log");
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
    public MerchantPara getMerchantPara() throws TrxException {
        if (!iIsInitialedWeb) {
            try {
                paraWeb = MerchantParaWeb.getUniqueInstance();
            } catch (TrxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            init(paraWeb);
            iIsInitialedWeb = true;
        }
        return paraWeb;
    }

    public byte[] readFile(String filePath) {
        //create file object    
        File file = new File(filePath);
        byte fileContent[] = null;
        try {
            FileInputStream fin = new FileInputStream(file);
            fileContent = new byte[(int) file.length()];
            fin.read(fileContent);

        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        }
        return fileContent;
    }
}