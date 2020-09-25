package com.example.chatdemo.exceptions;

public class RegisteredUsernameException extends RuntimeException {

    public RegisteredUsernameException(String username) {
        super("Username: " + username + " already exists");
    }
}
