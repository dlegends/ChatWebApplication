package com.example.chatdemo.controllers;

import com.example.chatdemo.exceptions.InvalidChatMessageException;
import com.example.chatdemo.services.ChatMessageService;
import com.example.chatdemo.entities.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

    private static final Logger logger = LoggerFactory.getLogger(ChatMessageController.class);

    @Autowired
    ChatMessageService chatMessageService;

    /**
     * Given message, save it and broadcast to people listening to the channel
     * @param message SimpleChatMessage because ChatMessage is too
     *                difficult to create on client-side.
     * @return ChatMessage formed from the SimpleChatMessage
     */
    @MessageMapping("/chatrooms/{chatroomId}")
    @SendTo("/topic/chatrooms/{chatroomId}")
    public ChatMessageDTO chat(@DestinationVariable Long chatroomId, ChatMessageDTO message) {
        if (!chatroomId.equals(message.getChatroomId())) {
            throw new InvalidChatMessageException(
                    "Destination Variable does not match messages's chatroom id");
        }
        return chatMessageService.saveChatMessage(message);
    }
}
