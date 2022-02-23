package com.sjy.secondskill.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀活动实体
 */
@Data
public class ActSecondKill implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private long goodsId;//参与活动的商品
    private String name; //商品名称
    private String status; //是否开启
    private Date beginTime;  //抢购活动开始时间
    private Date endTime;   //结束时间
    private String Date;
    /**
     * 活动时长
     **/
    private long count; //库存
    private long sale;  //销售个数
    private long version; //版本


}
