package com.example.chatdemo.exceptions;

public class AlreadyLoggedInException extends RuntimeException {
    public AlreadyLoggedInException() {
        super("Already logged in account");
    }
}
