package com.example.chatdemo.repositories;

import com.example.chatdemo.entities.ChatMessage;
import com.example.chatdemo.entities.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    @Query(value = "SELECT cr FROM Chatroom cr " +
            "LEFT JOIN FETCH cr.chatMessages " +
            "LEFT JOIN FETCH cr.participants " +
            "WHERE cr.id = :chatroom_id")
    Optional<Chatroom> joinFetchFindById(
            @Param(value = "chatroom_id") Long chatroomId);

    Set<Chatroom> findByTitleContainingIgnoreCase(String title);
}
