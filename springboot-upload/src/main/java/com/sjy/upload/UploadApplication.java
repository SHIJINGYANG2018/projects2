package com.sjy.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;

@SpringBootApplication
@RestController
@RequestMapping("api")
public class UploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class, args);
    }
    @PostMapping("/upload")
    public void upload(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws Exception {
        //单文件处理
        //获取上传文件的原始名称
        String originalFilename = file.getOriginalFilename();
        System.out.println("上传文件的大小：" + file.getSize());
        System.out.println("上传文件的类型:" + file.getContentType());
        System.out.println("上传文件时所用的属性名称【key】：" + file.getName());
        System.out.println(getRequestJson(request));

        File dest = new File(originalFilename);
        file.transferTo(dest);
    }

    @PostMapping("/str")
    public void upload(HttpServletRequest request,String str, @RequestParam("inta")Integer inta){
        System.out.println(request.getParameter("str"));
        System.out.println(str);
        System.out.println(inta);
        System.out.println(getRequestJson(request));

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
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }

        return json;
    }
}
