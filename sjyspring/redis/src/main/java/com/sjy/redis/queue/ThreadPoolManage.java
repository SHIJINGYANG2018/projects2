
package com.sjy.redis.queue;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 */
public class ThreadPoolManage {

    private ThreadPoolExecutor pool = null;

    // 线程池维护线程的最小数量
    private static int corePoolSize = 1;
    // 线程池维护线程的最大数量
    private static int maximumPoolSize = 3;
    // 空闲线程的存活时间
    private static long keepAliveTime = 30;
    // 时间单位,现有纳秒,微秒,毫秒,秒枚举值
    private TimeUnit unit = TimeUnit.MINUTES;

    /**
     * 线程池初始化方法
     * <p>
     * corePoolSize 核心线程池大小----1
     * <p>
     * maximumPoolSize 最大线程池大小----3
     * <p>
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
     * <p>
     * TimeUnit keepAliveTime 时间单位----TimeUnit.MINUTES
     * <p>
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(5)====5容量的阻塞队列
     * <p>
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
     * <p>
     * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,即当提交第8个任务时(前面线程都没有执行完
     * ),任务会交给RejectedExecutionHandler来处理
     */
    public ThreadPoolManage() {

        pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new ArrayBlockingQueue<Runnable>(5),
                new CustomThreadFactory(), new CustomRejectedExecutionHandler());
    }

    private class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {

            Thread t = new Thread(r);
            String threadName = ThreadPoolManage.class.getSimpleName() + count.addAndGet(1);
            // t.setName(threadName);
            return t;
        }
    }

    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            try {
                // 核心改造点，由blockingqueue的offer改成put阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 运行任务
    public void process(MsgVo MsgVo) {

        pool.execute(new SyncRefundThread(MsgVo));
    }

    // 销毁
    public void destory() {

        if (pool != null) {
            pool.shutdownNow();
        }
    }

}
