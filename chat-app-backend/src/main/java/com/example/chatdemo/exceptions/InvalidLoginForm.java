package com.example.chatdemo.exceptions;

public class InvalidLoginForm extends RuntimeException {
    public InvalidLoginForm() {
        super("Invalid login form");
    }
}
