package com.sjy.prometheus.prometheus;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Component
public class InitPrometheus implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    PrometheusMeterRegistry meterRegistry;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new MyMetrics();

        CollectorRegistry prometheusRegistry = meterRegistry.getPrometheusRegistry();

        prometheusRegistry.register(MyMetrics.requestCounter);
        prometheusRegistry.register(MyMetrics.inprogressRequests);
        prometheusRegistry.register(MyMetrics.requestLatencyHistogram);
        prometheusRegistry.register(MyMetrics.requestLatency);
    }
}