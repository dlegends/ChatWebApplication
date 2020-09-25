package com.example.chatdemo.exceptions;

public class LoginFailureException extends RuntimeException {
    public LoginFailureException() {
        super("Either username or password does not match");
    }
}
