package com.sjy.websocketnetty.chatbotTest;

import lombok.*;

import java.io.Serializable;

/**
 * 移动 RCS 消息发送
 */

public class RcsSubmitMessage  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String seqNum;
    private String tid;
    private Long userId;      //用户ID
    private Long chatbotId;   //  chatbotId号
    private String chatbotNumber;  // chatbotNumber号
    private String appSecret;//chatbot密码
    private String appid;
    private Integer priority;  //优先级
    private Integer contentType; // 5G消息类型
    private String content; // Rcs消息内容
    private String recvTime = ""; // 接收消息时间存放的时间戳
    private byte retryTime = 3; // 消息重发次数,默认3
    private String isLoaded; // 是否已经回送
    private String messageId = "";  //消息提交ID

    private String desAddress; //发送手机号 一个手机号
    private String sip;     //手机号所属运营商
    private String sendTime;   //消息预发送时间
    private String realSendTime;   //消息真实发送时间

    private String inReplyToContributionId;

    /**
     * 是否发送2G短信
     */
    private Boolean shortMessageSupported = false;
    /**
     * 2G短信内容
     */
    private String smsContent;


    /**
     * 是否回落UP1.0消息
     */
    private Boolean fallbackSupported = false;
    /**
     * 消息格式
     */
    private Integer fallbackContentType;
    /**
     * 消息内容
     */
    private String fallbackRcsBodyText;


    private String submitStatus ; // 消息提交运营商状态 0:成功 1:失败
    private String vcode = "1"; // 接受消息状态 0:成功 1:失败
    private String deliveryStatus = "1"; // 消息状态报告通知 0:成功 1:失败
    private String deliveryStatusDescription; // 消息状态报告描述


    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatbotId() {
        return chatbotId;
    }

    public void setChatbotId(Long chatbotId) {
        this.chatbotId = chatbotId;
    }

    public String getChatbotNumber() {
        return chatbotNumber;
    }

    public void setChatbotNumber(String chatbotNumber) {
        this.chatbotNumber = chatbotNumber;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(String recvTime) {
        this.recvTime = recvTime;
    }

    public byte getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(byte retryTime) {
        this.retryTime = retryTime;
    }

    public String getIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(String isLoaded) {
        this.isLoaded = isLoaded;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDesAddress() {
        return desAddress;
    }

    public void setDesAddress(String desAddress) {
        this.desAddress = desAddress;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getRealSendTime() {
        return realSendTime;
    }

    public void setRealSendTime(String realSendTime) {
        this.realSendTime = realSendTime;
    }

    public String getInReplyToContributionId() {
        return inReplyToContributionId;
    }

    public void setInReplyToContributionId(String inReplyToContributionId) {
        this.inReplyToContributionId = inReplyToContributionId;
    }

    public Boolean getShortMessageSupported() {
        return shortMessageSupported;
    }

    public void setShortMessageSupported(Boolean shortMessageSupported) {
        this.shortMessageSupported = shortMessageSupported;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Boolean getFallbackSupported() {
        return fallbackSupported;
    }

    public void setFallbackSupported(Boolean fallbackSupported) {
        this.fallbackSupported = fallbackSupported;
    }

    public Integer getFallbackContentType() {
        return fallbackContentType;
    }

    public void setFallbackContentType(Integer fallbackContentType) {
        this.fallbackContentType = fallbackContentType;
    }

    public String getFallbackRcsBodyText() {
        return fallbackRcsBodyText;
    }

    public void setFallbackRcsBodyText(String fallbackRcsBodyText) {
        this.fallbackRcsBodyText = fallbackRcsBodyText;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryStatusDescription() {
        return deliveryStatusDescription;
    }

    public void setDeliveryStatusDescription(String deliveryStatusDescription) {
        this.deliveryStatusDescription = deliveryStatusDescription;
    }
}
