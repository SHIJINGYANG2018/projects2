package com.sjy.upload.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author qinpeng
 * 解决request.getInputStream()只能够读取一次问题
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] bytes;
    private  Map<String, String[]> parameterMap;
    private JSONObject bodyJson;
    private  MultipartFile file;
    private Logger logger = LoggerFactory.getLogger(RequestWrapper.class);


    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        parameterMap = request.getParameterMap();
        /*// 检测是否为上传请求
        String contentType = request.getContentType();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
            MultipartHttpServletRequest multipartRequest =
                    WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            file = multipartRequest.getFile("file");
        }*/
        bytes = StreamUtils.copyToByteArray(request.getInputStream());
        String contentType = request.getContentType();
        if (request.getMethod().toLowerCase().equals("post")&&contentType.toLowerCase().contains("json")) {
            try {
                bodyJson = JSON.parseObject(new String(bytes));
            }catch (Exception e){
                logger.error("数据非JSON格式");
                bodyJson=new JSONObject();
            }
            System.out.println(bodyJson.toString());
        }
    }


    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = htmlEscape(values[i]);
        }
        return encodedValues;
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value != null) {
            return htmlEscape(value);
        }
        String[] values = parameterMap.get(name);
        if (values!=null) {
            value = values[0];
            if (value!=null) {
                return htmlEscape(value);
            }
        }
        if (!bodyJson.isEmpty()) {
            Object nameObj = bodyJson.get(name);
            if (nameObj!=null) {
                return nameObj.toString();
            }
        }
        return null;

    }

    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (value instanceof String) {
            htmlEscape((String) value);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    @Override
    public String getQueryString() {
        String value = super.getQueryString();
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    /**
     * 使用spring HtmlUtils 转义html标签达到预防xss攻击效果
     *
     * @param str
     * @see HtmlUtils#htmlEscape
     */
    protected String htmlEscape(String str) {
        return HtmlUtils.htmlEscape(str);
    }

}
