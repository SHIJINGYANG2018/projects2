package com.sjy.redis.shishipaiming;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 0L;

    private Integer id; //id
    private String name; //姓名
    private String url; //地址

    public User() {
    }

    public User(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
