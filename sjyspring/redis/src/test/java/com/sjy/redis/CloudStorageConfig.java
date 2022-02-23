package com.sjy.redis;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 云存储配置信息
 *
 * @author mocentre
 */
@Data
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;


    private String type;
/*

    //七牛绑定的域名
    private String qiniuDomain;
    //七牛路径前缀
    private String qiniuPrefix;
    //七牛ACCESS_KEY
    private String qiniuAccessKey;
    //七牛SECRET_KEY
    private String qiniuSecretKey;
    //七牛存储空间名
    private String qiniuBucketName;*/

    //阿里云绑定的域名
    private String aliyunDomain;
    //阿里云路径前缀
    private String aliyunPrefix;
    //阿里云EndPoint
    private String aliyunEndPoint;
    //阿里云AccessKeyId
    private String aliyunAccessKeyId;
    //阿里云AccessKeySecret
    private String aliyunAccessKeySecret;
    //阿里云BucketName
    private String aliyunBucketName;


    /**
     * 又拍云空间名称
     */
    private String upyunBucjetName;


    //阿里云路径前缀
    private String upaiyunPrefix;
    /**
     * 又拍云操作员 用户名
     */
    private String upyunOperatorName;
    /**
     * 又拍云操作员密码
     */
    private String upyunOperatorPwd;

    /**
     * 又拍云密钥
     */
    private String upyunSecret;
    /**
     * 又拍云展示域名
     */
    private String upyunShowDomain;

    /**
     * 又拍云上传域名
     */
    private String upyunApiDomain;


    /**
     * 本地路径前缀
     */
    private String localPrefix;

/*
    //腾讯云绑定的域名
    private String qcloudDomain;
    //腾讯云路径前缀
    private String qcloudPrefix;
    //腾讯云AppId
    private Integer qcloudAppId;
    //腾讯云SecretId
    private String qcloudSecretId;
    //腾讯云SecretKey
    private String qcloudSecretKey;
    //腾讯云BucketName
    private String qcloudBucketName;
    //腾讯云COS所属地区
    private String qcloudRegion;
*/

}
