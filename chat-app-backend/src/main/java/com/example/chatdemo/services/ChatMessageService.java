package com.example.chatdemo.services;

import com.example.chatdemo.entities.ChatMessage;
import com.example.chatdemo.entities.ChatMessageDTO;

public interface ChatMessageService {
    ChatMessageDTO saveChatMessage(ChatMessageDTO chatMessage);
}
