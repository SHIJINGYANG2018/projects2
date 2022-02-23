package com.sjy.secondskill.bean;

import lombok.Data;

@Data
public class Order {
    /**
     * 店铺id
     **/
    private Long shopId;

    /**
     * 订单编号
     **/
    private String orderNum;

    /**
     * 是否开发票
     **/
    private Integer isInvoice;

    /**
     * 订单总价(实付总金额)
     **/
    private Long totalPrice;

    /**
     * 配送费
     **/
    private Long transFee;

    /**
     * 下单时间
     **/
    private java.util.Date orderTime;

    /**
     * 订单状态（等待买家付款 等待卖家发货 等待确认收货 交易成功 交易关闭）
     **/
    private String orderStatus;

    /**
     * 处理状态
     **/
    private String dealStatus;

    /**
     * 用户备注
     **/
    private String remark;

    /**
     * 收货地址id
     **/
    private Long addressId;

    /**
     * 用户id
     **/
    private Long customerId;

    /**
     * 收件人
     **/
    private String recipient;

    /**
     * 联系电话
     **/
    private String telephone;

    /**
     * 收货地址
     **/
    private String address;

    /**
     * 使用优惠券编码
     **/
    private String couponSn;

    /**
     * 优惠金额
     **/
    private Long couponMoney;

    /**
     * 订单类型
     **/
    private String orderType;

    /**
     * 不同类型的id
     **/
    private Long typeId;

    /**
     * 支付编号
     **/
    private String paymentNum;

    /**
     * 来源类型（tehui:商城 abc:农行客户端）
     **/
    private String sourceType;

    /**
     * 复制订单号
     **/
    private String copyOrderNum;
}
