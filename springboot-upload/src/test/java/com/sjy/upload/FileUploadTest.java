package com.sjy.upload;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/19.
 */
public class FileUploadTest {

    public static void main(String[] args) throws IOException {
        //String webCallUrl ="http://api-test.vlink.cn/interface/open/v1/voiceUpload";
        String webCallUrl ="http://localhost:8094//interface/open/v1/voiceUpload";
        upload(webCallUrl,"");
    }
    private final static Logger log = LoggerFactory.getLogger(FileUploadTest.class);
    public static void upload(String url,String filePath) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String webCallEnterpriseId="5200303";
        String webCallToken ="2d205f13363d913654769037c52c0d86";

        long timeStamp = System.currentTimeMillis() / 1000;
        String sign = UploadApplicationTests.encode(webCallEnterpriseId + webCallToken + timeStamp);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("appId",webCallEnterpriseId);
            builder.addTextBody("timestamp",String.valueOf(timeStamp));
            builder.addTextBody("sign",sign);
           // builder.addBinaryBody("file",new File("C:\\Users\\admin\\Desktop\\test.mp3"));
        ContentType contentType = ContentType.create("audio/mp3");

        builder.addBinaryBody("file",new File("C:\\Users\\admin\\Desktop\\test.mp3"),contentType,"");

            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
        System.out.println(EntityUtils.toString(response.getEntity()));

    }

  
}