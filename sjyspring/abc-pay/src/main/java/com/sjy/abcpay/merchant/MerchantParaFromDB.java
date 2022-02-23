/*
package com.sjy.abcpay.merchant;

import com.abc.pay.client.*;
import com.sjy.abcpay.bean.PayConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;

*/
/**
 * ���幤��-���˹����������ݿ��ж�ȡ����
 * �������ֲ�Ʒ��MerchantParaWeb��
 * <p>
 * �̻��������ļ���Դ����
 * <p>
 * ֧���ó�ʼ���Լ��ĵ���֧������
 * <p>
 * ��ʼ���
 * <p>
 * ����������ʼ��
 *
 * @param logPathp
 * @param errorUrlp
 * @param truststorePath
 * @param cerPath
 * <p>
 * MerchantParaFromDB�����ʵ��getMerchantPara����������MerchantPara���Ͷ���
 * �����ݿ��ж�ȡ�����
 * Ĭ��ʵ��Ϊweb
 * <p>
 * �̻��������ļ���Դ����
 * <p>
 * ֧���ó�ʼ���Լ��ĵ���֧������
 * <p>
 * ��ʼ���
 * <p>
 * ����������ʼ��
 * @param logPathp
 * @param errorUrlp
 * @param truststorePath
 * @param cerPath
 * <p>
 * MerchantParaFromDB�����ʵ��getMerchantPara����������MerchantPara���Ͷ���
 * �����ݿ��ж�ȡ�����
 * Ĭ��ʵ��Ϊweb
 *//*

public class MerchantParaFromDB extends MerchantParaFactory {


	private static PayConfig payConfig;

	private static String logPath;
	private static String errorUrl;
	private  static String truststoreUrl ;
	private static   String cerUrl;



	*/
/**
 * �̻��������ļ���Դ����
 *//*

    private static MerchantPara paraWeb = null;

	*/
/**
 * ֧���ó�ʼ���Լ��ĵ���֧������
 *//*

	private  Long shopId = 0L;

	public  Long getShopId() {
		return shopId;
	}

	public  void setShopId(Long id) {
		shopId = id;
	}

	public  void setPayConfig(PayConfig payConfigp) {
		this.payConfig = payConfigp;
	}

	*/
/**
 * ��ʼ���
 *//*

    private static boolean iIsInitialedWeb = false;
	
	@Override
	public void refreshConfig() throws TrxException {
		iIsInitialedWeb = false;
	}


	*/
/**
 * 	����������ʼ��
 * @param logPathp
 * @param errorUrlp
 * @param truststorePath
 * @param cerPath
 *//*

	public  void initBase(String logPathp,String errorUrlp,String truststorePath ,String cerPath){
		logPath = logPathp;
		errorUrl = errorUrlp;
		truststoreUrl = truststorePath;
		cerUrl = cerPath;
	}
	public void init(MerchantPara para){

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
			para.setMerchantErrorURL(errorUrl);
							
			//##����֧��ƽ̨ϵͳ���ö� - �������� - ����֤����·����ʹ��ͱ��ش��·����ƥ�䣨����·����
			//#����֧��ƽ̨֤��
			para.setTrustPayCertFileName(cerUrl);

			//#ũ�и�֤���ļ�
			para.setTrustStoreFileName(truststoreUrl);
			
			//#ũ�и�֤���ļ�����
			para.setTrustStorePassword("changeit");
			
			//�����̻���š�����Ƕ��̻�����iMerchantIDList���ö�����¼
			ArrayList<String> iMerchantIDList = new ArrayList<String>();
			iMerchantIDList.add(payConfig.getPartner());
			para.setMerchantIDList(iMerchantIDList);
			
      		FileUtil util = new FileUtil();
            
			//�����̻�֤�顣����Ƕ��̻�����iMerchantCertNameList���ö�����¼��ע�⣺�̻�֤������˳��Ҫ���̻����˳��һ��
			ArrayList<byte[]> iMerchantCertList = new ArrayList<byte[]>();
			iMerchantCertList.add(util.readFile(payConfig.getCacertPath()));
			para.setMerchantCertFileList(iMerchantCertList);
            
      //�����̻�֤�����롣����Ƕ��̻�����iMerchantPasswordList���ö�����¼��ע�⣺����˳��Ҫ���̻����˳��һ��
			ArrayList<String> iMerchantPasswordList = new ArrayList<String>();
			iMerchantPasswordList.add(payConfig.getSignKey());// �̻�˽Կ����
			para.setMerchantCertPasswordList(iMerchantPasswordList);


			File file = new File(logPath);
			if (!file.exists()) {
			file.mkdirs();
			}
			//#������־�ļ����Ŀ¼
			para.setLogPath(logPath);
			//#֤�鴢��ý��
			para.setMerchantKeyStoreType("0");
			
			//һ���̻���ѡ���ļ�֤��
      if (para.getMerchantKeyStoreType().equals(MerchantPara.KEY_STORE_TYPE_FILE)) {
          CertHelper.bindMerchantCertificate(para, iMerchantCertList, iMerchantPasswordList);
      }
      else if (para.getMerchantKeyStoreType().equals(MerchantPara.KEY_STORE_TYPE_SIGN_SERVER)) {
      }
      else {
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

    */
/**
 * MerchantParaFromDB�����ʵ��getMerchantPara����������MerchantPara���Ͷ���
 * �����ݿ��ж�ȡ�����
 * Ĭ��ʵ��Ϊweb
 *//*

	@Override
	public MerchantPara getMerchantPara() throws TrxException {


        if (!iIsInitialedWeb) {
        	try {
        		paraWeb = MerchantParaWeb.getUniqueInstance();
    		} catch (TrxException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	init(paraWeb);
        }
		return paraWeb;
	}
}*/
