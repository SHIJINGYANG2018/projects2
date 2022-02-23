package com.mocentre.recharge.entity;

import java.io.Serializable;

/**
 * 回调接受对象
 */
public class CallBack implements Serializable {

    private String phone;
    private String recharge_number;
    private Integer status;
    private Integer rsp_code;
    private String rsp_msg;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecharge_number() {
        return recharge_number;
    }

    public void setRecharge_number(String recharge_number) {
        this.recharge_number = recharge_number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRsp_code() {
        return rsp_code;
    }

    public void setRsp_code(Integer rsp_code) {
        this.rsp_code = rsp_code;
    }

    public String getRsp_msg() {
        return rsp_msg;
    }

    public void setRsp_msg(String rsp_msg) {
        this.rsp_msg = rsp_msg;
    }

    @Override
    public String toString() {
        return "CallBack{" +
                "phone='" + phone + '\'' +
                ", recharge_number='" + recharge_number + '\'' +
                ", status=" + status +
                ", rsp_code=" + rsp_code +
                ", rsp_msg='" + rsp_msg + '\'' +
                '}';
    }
}
