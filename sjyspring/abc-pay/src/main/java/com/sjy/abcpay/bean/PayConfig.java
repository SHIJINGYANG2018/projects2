package com.sjy.abcpay.bean;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 类PayConfig.java的实现描述：付款方式配置
 *
 * @author 2016年12月14日 下午3:58:17
 */
@Data
@TableName("buy_t_pay_config")
public class PayConfig {

    private static final long serialVersionUID = 6130284064660006596L;

    /**
     * 商家商户号（mch_id）
     **/
    private String partner;

    /**
     * 商户名称
     **/
    private String partnerName;

    /**
     * 收款账号
     **/
    private String sellerId;

    /**
     * 私钥路径(支付宝)
     **/
    private String privateKey;

    /**
     * 公钥路径(支付宝)
     **/
    private String publicKey;

    /**
     * 签名方式(支付宝默认RSA)
     **/
    private String signType;

    /**
     * 编码方式，默认utf-8
     **/
    private String charset;

    /**
     * ca证书路径    （abc农行pfx证书）
     **/
    private String cacertPath;

    /**
     * 访问方式(http)
     **/
    private String transport;

    /**
     * 支付类型(alipay,wxpay,kcode,abcpay)
     **/
    private String payType;

    /**
     * 通知方式
     **/
    private String notifyType;

    /**
     * appid
     **/
    private String appid;

    /**
     * appid对应的接口密钥
     **/
    private String appsecret;

    /**
     * 是否开启(0,不开启)
     **/
    private Integer open;

    /**
     * 签名密钥(微信) （农行支付的merchantPassword）
     **/
    private String signKey;

    /**
     * 扩展配置项(json存储)
     **/
    private String extKey;

    /**
     * 店铺id
     **/
    private Long shopId;


}
