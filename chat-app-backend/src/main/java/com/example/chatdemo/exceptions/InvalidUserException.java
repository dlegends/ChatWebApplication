package com.example.chatdemo.exceptions;

/**
 * Exception for unmatching user information
 */
public class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super("Invalid user");
    }
}
