package com.sjy.test;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.sjy.common.utils.OkhttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class TestApplicationTests2 {
    private static final String KEY = "some_method_xxxx";
    private static final String KEY1 = "some_method_xxxx1";
    public static void main(String[] args) throws InterruptedException {
        initDegradeRule();
        registerStateChangeObserver();
        while (true) {
            new Thread(() -> {
                Entry entry = null;
                try {
                    entry = SphU.entry(KEY);
                    System.out.println(entry);
                    int i = ThreadLocalRandom.current().nextInt(3, 7);

                    System.out.println("线程id "+Thread.currentThread().getId() + "   " +System.currentTimeMillis()+    "  " + i);
                    String s = OkhttpUtils.get("http://127.0.0.1:8202/v1/get/"+i);

                    System.out.println("线程id "+Thread.currentThread().getId() +"   " +System.currentTimeMillis()+" "+i+"    "+s);
                } catch (BlockException e) {
                    System.err.println("线程id "+Thread.currentThread().getId() + "   "+System.currentTimeMillis()+"    "+e);
                } finally {
                    if (entry != null) {
                        entry.exit();
                    }
                }

            }).start();
            new Thread(() -> {
                Entry entry = null;
                try {
                    entry = SphU.entry(KEY1);
                    System.out.println(entry);
                    int i = ThreadLocalRandom.current().nextInt(6, 11);

                    System.out.println("线程id "+Thread.currentThread().getId() + "   " +System.currentTimeMillis()+    "  " + i);
                    String s = OkhttpUtils.get("http://127.0.0.1:8202/v1/get1/"+i);

                    System.out.println("线程id "+Thread.currentThread().getId() +"   " +System.currentTimeMillis()+" "+i+"    "+s);
                } catch (BlockException e) {
                    System.err.println("线程id "+Thread.currentThread().getId() + "   "+System.currentTimeMillis()+"    "+e);
                } finally {
                    if (entry != null) {
                        entry.exit();
                    }
                }

            }).start();
            int j = ThreadLocalRandom.current().nextInt(100, 300);
            TimeUnit.MILLISECONDS.sleep(j);
        }
    }

    //初始化规则
    private static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule(KEY)
                .setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType())
                // Max allowed response time
                .setCount(4000)
                // Retry timeout (in second)//
                //熔断时长，单位为 s
                .setTimeWindow(1)
                //慢调用比例阈值，仅慢调用比例模式有效（1.8.0 引入）
                .setSlowRatioThreshold(0.1)
                //熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断（1.7.0 引入）
                .setMinRequestAmount(10)
                //统计时长（单位为 ms），如 60*1000 代表分钟级（1.8.0 引入）
                .setStatIntervalMs(2000);
        rules.add(rule);
        rule = new DegradeRule(KEY1)
                .setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType())
                // Max allowed response time
                .setCount(7000)
                // Retry timeout (in second)//
                //熔断时长，单位为 s
                .setTimeWindow(1)
                //慢调用比例阈值，仅慢调用比例模式有效（1.8.0 引入）
                .setSlowRatioThreshold(0.7)
                //熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断（1.7.0 引入）
                .setMinRequestAmount(5)
                //统计时长（单位为 ms），如 60*1000 代表分钟级（1.8.0 引入）
                .setStatIntervalMs(1000);
        rules.add(rule);

        // 熔断规则时慢调用
        // 在statIntervalMs 毫秒内,请求大于 minRequestAmount  都会触发 熔断检测，
        // 当响应大于count 5000ms时，则将这个请求成为慢调用，当慢调用的比例大于 slowRatioThreshold时才触发熔断，熔断时间为timeWindow s。
        //经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求响应时间小于设置的慢调用 RT 则结束熔断，若大于设置的慢调用 RT 则会再次被熔断。

        DegradeRuleManager.loadRules(rules);
        System.out.println("Degrade rule loaded: " + rules);
    }


    // 状态监听
    private static void registerStateChangeObserver() {
        EventObserverRegistry.getInstance().addStateChangeObserver("111logging",
                (prevState, newState, rule, snapshotValue) -> {
                    if (newState == CircuitBreaker.State.OPEN) {
                        System.err.println("111logging 线程id "+Thread.currentThread().getId() + "   "+ String.format("%s -> OPEN at %d, snapshotValue=%.2f", prevState.name(),
                                TimeUtil.currentTimeMillis(), snapshotValue)+"rule"+rule);
                    } else {
                        System.err.println(" 111logging 线程id "+Thread.currentThread().getId() + "   "+ String.format("%s -> %s at %d", prevState.name(), newState.name(),
                                TimeUtil.currentTimeMillis())+"rule"+rule);
                    }
                });
        EventObserverRegistry.getInstance().addStateChangeObserver("logging111",
                (prevState, newState, rule, snapshotValue) -> {
                    if (newState == CircuitBreaker.State.OPEN) {
                        System.err.println("logging111 线程id "+Thread.currentThread().getId() + "   "+ String.format("%s -> OPEN at %d, snapshotValue=%.2f", prevState.name(),
                                TimeUtil.currentTimeMillis(), snapshotValue)+"rule"+rule);
                    } else {
                        System.err.println("logging111 线程id "+Thread.currentThread().getId() + "   "+ String.format("%s -> %s at %d", prevState.name(), newState.name(),
                                 TimeUtil.currentTimeMillis())+"rule"+rule);
                    }
                });
    }
}
