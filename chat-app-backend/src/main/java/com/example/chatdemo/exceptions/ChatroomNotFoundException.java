package com.example.chatdemo.exceptions;

public class ChatroomNotFoundException extends RuntimeException {
    public ChatroomNotFoundException(Long id) {
        super("Chatroom with id " + id + " does not exist");
    }
}
