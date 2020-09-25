package com.example.chatdemo.controllers;

import com.example.chatdemo.ChatroomConfig;
import com.example.chatdemo.entities.*;
import com.example.chatdemo.services.ChatroomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class ChatroomController {
    @Autowired
    ChatroomService chatroomService;

    private static final Logger logger = LoggerFactory.getLogger(ChatroomController.class);

    @PostMapping(path = "/chatrooms")
    public ChatroomDTO createChatroom(@RequestBody ChatroomConfig config) {
        // Gotta add location of resource in the header
        return chatroomService.createChatroom(config.getTitle());
    }

    @GetMapping(path = "/chatrooms")
    public Set<ChatroomDTO> getChatrooms() {
        return chatroomService.getChatrooms();
    }

    @GetMapping(path = "/chatrooms/{id}")
    public ChatroomDTO getChatroom(@PathVariable Long id) {
        return chatroomService.getChatroom(id);
    }

    @PostMapping(path = "/chatrooms/{chatroomId}/participants")
    public Set<UserDTO> addUserToChatroom(@PathVariable Long chatroomId,
                                     @RequestBody Long userId) {
        return chatroomService.addUserToChatroom(chatroomId, userId);
    }

    @GetMapping(path = "/chatrooms/{chatroomId}/participants")
    public Set<UserDTO> getParticipants(@PathVariable(name = "chatroomId") Long id) {
        return chatroomService.getParticipants(id);
    }

    @DeleteMapping(path = "/chatrooms/{chatroomId}/participants/{userId}")
    public void removeParticipant(@PathVariable Long chatroomId,
                                  @PathVariable Long userId) {
        chatroomService.removeParticipant(chatroomId, userId);
    }

    @GetMapping(path = "/chatrooms/{id}/messages")
    public Set<ChatMessageDTO> getChatMessages(@PathVariable Long id) {
        return chatroomService.getChatMessages(id);
    }

    @GetMapping(path = "/chatrooms/search")
    public Set<ChatroomDTO> getFilteredChatrooms(
            @RequestParam(name = "q", defaultValue = "") String title
    ) {
        if (title.equals("")) return chatroomService.getChatrooms();
        return chatroomService.getFilteredChatrooms(title);
    }
}
