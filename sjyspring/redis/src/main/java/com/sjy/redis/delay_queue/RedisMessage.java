package com.sjy.redis.delay_queue;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RedisMessage implements Serializable {

    /** 消息队列组 **/
    private String group;

    /**
     * 消息id
     */
    private String id;

    /**
     * 消息延迟/ 秒
     */
    @NotNull(message = "消息延时时间不能为空")
    private long delay;

    /**
     * 消息存活时间 单位：秒
     */
    @NotNull(message = "消息存活时间不能为空")
    private int ttl;
    /**
     * 消息体，对应业务内容
     */
    private Object body;
    /**
     * 创建时间，如果只有优先级没有延迟，可以设置创建时间为0
     * 用来消除时间的影响
     */
    private long createTime;

}
