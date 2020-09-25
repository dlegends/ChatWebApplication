package com.example.chatdemo.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessageDTO {
    Long id;
    String content;
    UserDTO sender;
    Long chatroomId;
    LocalDateTime sentOn;
}
