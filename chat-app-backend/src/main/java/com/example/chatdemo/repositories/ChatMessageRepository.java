package com.example.chatdemo.repositories;

import com.example.chatdemo.entities.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatMessageRepository extends
        PagingAndSortingRepository<ChatMessage, Long> {

    @Query(value = "SELECT cm FROM ChatMessage cm WHERE cm.chatroom.id = :chatroom_id")
    Set<ChatMessage> getChatMessagesByChatroomId(
            @Param(value = "chatroom_id") Long chatroomId);

    @Query(value = "SELECT cm FROM ChatMessage cm " +
            "LEFT JOIN FETCH cm.sender " +
            "LEFT JOIN FETCH cm.chatroom " +
            "WHERE cm.id = :chat_message_id")
    Optional<ChatMessage> joinFetchFindById(
            @Param(value = "chat_message_id") Long chatMessageId);
}
