package com.example.dacas.entity;

/**
 * Created by qingf on 2016/9/28.
 */

public class Person {
    /**
     * username : songsong
     * password : 123456
     */

    private String username;
    private String password;
    private String ticket;

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
