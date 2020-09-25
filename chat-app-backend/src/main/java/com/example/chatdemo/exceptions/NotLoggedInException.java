package com.example.chatdemo.exceptions;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException(Long id) {
        super("User with id " + id + " is not logged in");
    }
}
