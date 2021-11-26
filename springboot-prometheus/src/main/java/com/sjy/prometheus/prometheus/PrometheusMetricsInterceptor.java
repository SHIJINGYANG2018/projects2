package com.sjy.prometheus.prometheus;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrometheusMetricsInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int status = response.getStatus();
        MyMetrics.requestCounter.labels(requestURI, method, String.valueOf(status)).inc();
        MyMetrics.inprogressRequests.labels(requestURI,method).inc();

        MyMetrics.histogramRequestTimer = MyMetrics.requestLatencyHistogram.labels(requestURI, method, String.valueOf(status)).startTimer();

        MyMetrics.requestTimer = MyMetrics.requestLatency.labels(requestURI, method, String.valueOf(status)).startTimer();
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int status = response.getStatus();

        MyMetrics.requestCounter.labels(requestURI, method, String.valueOf(status)).inc();

        MyMetrics.inprogressRequests.labels(requestURI,method).dec();

        MyMetrics.histogramRequestTimer.observeDuration();

        MyMetrics.requestTimer.observeDuration();

        super.afterCompletion(request, response, handler, ex);
    }
}