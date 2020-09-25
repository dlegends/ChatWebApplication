package com.example.chatdemo;

public class JwtData {
    private final String token;
    private final Long id;

    public JwtData(String token, Long id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }
}
