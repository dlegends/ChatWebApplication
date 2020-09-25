package com.example.chatdemo.exceptions;

public class InvalidChatMessageException extends RuntimeException {
    public InvalidChatMessageException(String error) {
        super(error);
    }
}
