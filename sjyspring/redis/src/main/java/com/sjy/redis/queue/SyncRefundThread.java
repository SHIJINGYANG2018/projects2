package com.sjy.redis.queue;

import org.springframework.util.StopWatch;

/**
 * ：执行任务线程
 */
public class SyncRefundThread implements Runnable {

    private MsgVo msgVo;

    public SyncRefundThread(MsgVo msgVo) {

        super();
        this.msgVo = msgVo;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StopWatch watch = new StopWatch();
        watch.start();
        // 处理时间
        watch.stop();
    }


}
