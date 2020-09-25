package com.example.chatdemo;

import com.example.chatdemo.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RegisteredUsernameException.class,
            AlreadyLoggedInException.class, InvalidUserException.class})
    public ResponseEntity<Object> handleRegisteredUsernameException(
            RegisteredUsernameException ex, WebRequest request) {

        return generateGeneralResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({LoginFailureException.class, ChatroomNotFoundException.class,
            UserNotFoundException.class, UserNotFoundInChatroomException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(
            LoginFailureException ex, WebRequest request) {

        return generateGeneralResponse(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> generateGeneralResponse(
            Exception ex, HttpStatus status) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, status);
    }
}
