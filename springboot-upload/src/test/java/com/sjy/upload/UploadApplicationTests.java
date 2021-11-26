package com.sjy.upload;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 *
 *
 *   <dependency>
 *      <groupId>cn.hutool</groupId>
 *      <artifactId>hutool-http</artifactId>
 *      <version>5.7.2</version>
 *   </dependency>
 */
public class UploadApplicationTests {
    private final static Logger logger = LoggerFactory.getLogger(UploadApplicationTests.class);
    public static void main(String[] args) {

       /* String webCallEnterpriseId="5200303";
        String webCallToken ="2d205f13363d913654769037c52c0d86";
        String webCallUrl ="http://api-test.vlink.cn/interface/open/v1/voiceUpload";*/
         String webCallEnterpriseId="5210183";
        String webCallToken ="cb70893bccb69e99b01ed8896ba024eb";
        String webCallUrl ="http://api-1.vlink.cn/interface/open/v1/voiceUpload";



        Map<String, Object> postParams = Maps.newHashMap();
        long timeStamp = System.currentTimeMillis() / 1000;
        String sign = encode(webCallEnterpriseId + webCallToken + timeStamp);
        postParams.put("appId", webCallEnterpriseId);
        postParams.put("timestamp", String.valueOf(timeStamp));
        postParams.put("sign", sign);
        postParams.put("description", "xxxx");
        //文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
        postParams.put("file", FileUtil.file("C:\\Users\\admin\\Desktop\\test.mp3"));
        String result= HttpUtil.post(webCallUrl, postParams);
        System.out.println(result);
    }
    public static String encode(String plainText) {
        return encode(plainText, null);
    }

    public static String encode(String plainText, String encoding) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null==encoding||"".equals(encoding)) {
                md.update(plainText.getBytes());
            } else {
                md.update(plainText.getBytes(encoding));
            }
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.toString(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString(), e);
        }
        return null;
    }

}
