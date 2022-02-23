package com.sjyspring.com;

/**
 * 国都下行消息定义: 标准cmpp2.0 + 扩展字段
 * 
 * 
 */
public class GdSubmitMessage extends GdMessage {


	// 版本号
	private int version = 1000000000;
	// 国都平台字段
	private int user_id; // int 4
	private String moduleName = ""; // 15字节
	private int gwkind = -1; // 1字节 运营商

	// 运营商标识常量， 用于处理submit_resp 中msg_id，因协议不同，长度不同的问题
	private byte isp_flag; // 1字节
	private String respMsgId = ""; // 50字节 sendid，RCS使用

	private int ecode = -1; // 1字节
	private String recv_time = ""; // 接收消息时间 17字节
	private String province = ""; // 省 50字节
	private String city = ""; // 市 50字节
	private String redis_list_name = "";// 队列名字 40字节
	private byte retry_time = 3; // 消息重发次数,默认3 (1字节)
	private String real_send_time = ""; // 消息实际发送时间 17字节
	private byte msgCount = 1; // 长短信实际下行条数

	private String wait_id; // 22byte
	private String access_flag; // 4byte
	private String tableName;

	private String classCode; // 信息分类
	private String channelcode; // 渠道号
	private String bankcode; // 机构号
	private String gwcode; // 通道号

	private String expand1; // 预留字段
	private String expand2; // 预留字段
	private String expand3; // 预留字段

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getGwkind() {
        return gwkind;
    }

    public void setGwkind(int gwkind) {
        this.gwkind = gwkind;
    }

    public byte getIsp_flag() {
        return isp_flag;
    }

    public void setIsp_flag(byte isp_flag) {
        this.isp_flag = isp_flag;
    }

    public String getRespMsgId() {
        return respMsgId;
    }

    public void setRespMsgId(String respMsgId) {
        this.respMsgId = respMsgId;
    }

    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public String getRecv_time() {
        return recv_time;
    }

    public void setRecv_time(String recv_time) {
        this.recv_time = recv_time;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRedis_list_name() {
        return redis_list_name;
    }

    public void setRedis_list_name(String redis_list_name) {
        this.redis_list_name = redis_list_name;
    }

    public byte getRetry_time() {
        return retry_time;
    }

    public void setRetry_time(byte retry_time) {
        this.retry_time = retry_time;
    }

    public String getReal_send_time() {
        return real_send_time;
    }

    public void setReal_send_time(String real_send_time) {
        this.real_send_time = real_send_time;
    }

    public byte getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(byte msgCount) {
        this.msgCount = msgCount;
    }

    public String getWait_id() {
        return wait_id;
    }

    public void setWait_id(String wait_id) {
        this.wait_id = wait_id;
    }

    public String getAccess_flag() {
        return access_flag;
    }

    public void setAccess_flag(String access_flag) {
        this.access_flag = access_flag;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getGwcode() {
        return gwcode;
    }

    public void setGwcode(String gwcode) {
        this.gwcode = gwcode;
    }

    public String getExpand1() {
        return expand1;
    }

    public void setExpand1(String expand1) {
        this.expand1 = expand1;
    }

    public String getExpand2() {
        return expand2;
    }

    public void setExpand2(String expand2) {
        this.expand2 = expand2;
    }

    public String getExpand3() {
        return expand3;
    }

    public void setExpand3(String expand3) {
        this.expand3 = expand3;
    }

    @Override
    public void setBuffer(byte[] buf) {

    }

    @Override
    public byte[] getBuffer() {
        return new byte[0];
    }

    @Override
    public String toString() {
        return "GdSubmitMessage{" +
                "user_id=" + user_id +
                ", redis_list_name='" + redis_list_name + '\'' +
                '}';
    }
}
