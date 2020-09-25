package com.example.chatdemo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private final Boolean result;
    private final String content;
}
