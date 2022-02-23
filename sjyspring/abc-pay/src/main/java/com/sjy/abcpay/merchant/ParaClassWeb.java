package com.sjy.abcpay.merchant;

import com.abc.pay.client.MerchantPara;
import com.abc.pay.client.TrxException;

public class ParaClassWeb extends MerchantPara {
    private static ParaClassWeb uniqueInstanceOf_MerchantPara = null;

    private static synchronized void syncInit() throws TrxException {
        if (uniqueInstanceOf_MerchantPara == null) {
            uniqueInstanceOf_MerchantPara = new ParaClassWeb();
        }

    }

    public static ParaClassWeb getUniqueInstance() throws TrxException {
        if (uniqueInstanceOf_MerchantPara == null) {
            syncInit();
        }

        return uniqueInstanceOf_MerchantPara;
    }

    private ParaClassWeb() {
    }

    @Override
    public void refreshConfig() {
        uniqueInstanceOf_MerchantPara = null;
    }
}
