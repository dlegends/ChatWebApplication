package com.example.chatdemo.exceptions;

public class UserNotFoundInChatroomException extends RuntimeException{
    public UserNotFoundInChatroomException(Long chatroomId, Long userId) {
        super("User with id" + userId + " not found in chatroom with id " + chatroomId);
    }
}
