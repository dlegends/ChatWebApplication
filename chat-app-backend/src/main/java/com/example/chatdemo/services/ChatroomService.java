package com.example.chatdemo.services;

import com.example.chatdemo.entities.*;

import java.util.Set;

public interface ChatroomService {
    ChatroomDTO createChatroom(String title);
    Set<ChatMessageDTO> getChatMessages(Long chatroomId);
    Set<ChatroomDTO> getChatrooms();
    ChatroomDTO getChatroom(Long chatroomId);
    Set<UserDTO> getParticipants(Long chatroomId);
    Set<UserDTO> addUserToChatroom(Long chatroomId, Long userId);
    Set<ChatroomDTO> getFilteredChatrooms(String title);
    void removeParticipant(Long chatroomId, Long userId);
}
