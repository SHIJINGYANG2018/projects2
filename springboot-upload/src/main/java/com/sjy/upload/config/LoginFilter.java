package com.sjy.upload.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author qinpeng on 2019/1/8.
 */
@Component
@WebFilter(urlPatterns = "/api/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        ServletRequest requestWrapper=null;
        if(servletRequest instanceof HttpServletRequest) {
            requestWrapper=new RequestWrapper((HttpServletRequest)servletRequest);
        }
        if(requestWrapper==null) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            System.out.println("------------------------------请求报文----------------------------------");
            System.out.println(getRequestJson((HttpServletRequest) requestWrapper));
            System.out.println("------------------------------请求报文----------------------------------");
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }
    /**
     * 获取请求中的json参数
     *
     * @param request
     * @return
     */
    public String getRequestJson(ServletRequest request) {
        InputStream in = null;
        String json = null;
        try {
            in = request.getInputStream();
            if (Objects.nonNull(in)) {
                byte[] bytes = new byte[1024];
                int c;
                StringBuilder sb = new StringBuilder();
                while ((c = in.read(bytes)) > 0) {
                    sb.append(new String(bytes, 0, c));
                }
                 json = sb.toString();
            }
        } catch (Exception e) {
            logger.error("loginfilter getRequestJson e", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                logger.error("loginfilter getRequestJson e", e);
            }
        }

        return json;
    }
    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
