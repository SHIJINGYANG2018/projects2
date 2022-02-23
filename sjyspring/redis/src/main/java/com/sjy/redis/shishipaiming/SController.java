package com.sjy.redis.shishipaiming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;


@RestController
public class SController {


    private final
    ShishiService shishiService;


    @Autowired
    public SController(ShishiService shishiService) {
        this.shishiService = shishiService;
    }

    @GetMapping("init")
    public void init() {
        shishiService.init();
    }

    @GetMapping("addSource")
    public void addSource() {
        shishiService.addSource();
    }

    @GetMapping("top10")
    public Set<ZSetOperations.TypedTuple<User>> top10() {

        return shishiService.top10();
    }

    @GetMapping("remove")
    public void remove() {

        shishiService.remove();
    }

    @GetMapping("all")
    public Set<User> all() {

        return shishiService.all();
    }

    @GetMapping("delete")
    public void delete() {

        shishiService.delete();
    }

    @PostMapping("upyun")
    public void upyunReturn(HttpServletRequest request, HttpServletResponse response) {
        String baseURL = getBaseURL(request);
        System.out.println("baseURL = " + baseURL);
        String id = request.getParameter("id");
        System.out.println("id = " + id);
    }

    public static String getBaseURL(HttpServletRequest request) {

        String cxtPath = request.getContextPath();
        int port = request.getServerPort();
        String baseUrl = "";
        if (port == 80) {
            baseUrl = "//" + request.getServerName() + cxtPath;
        } else {
            baseUrl = "//" + request.getServerName() + ":" + port + cxtPath;
        }
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
        }
        return baseUrl;
    }
}
