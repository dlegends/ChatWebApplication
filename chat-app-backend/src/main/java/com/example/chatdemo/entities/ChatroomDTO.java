package com.example.chatdemo.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatroomDTO {
    Long id;
    String title;
    Set<ChatMessageDTO> chatMessages = new HashSet<>();
    Set<UserDTO> participants = new HashSet<>();
}
