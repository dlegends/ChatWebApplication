package com.example.chatdemo.entities;

import com.example.chatdemo.serializers.CustomChatroomSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ChatMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    User sender;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    Chatroom chatroom;

    @Column(name = "sent_on")
    LocalDateTime sentOn;

    @Override
    public String toString() {
        String date = sentOn != null ? sentOn.toString() : "null";
        return String.format(
                "ChatMessage(id=%d, content=%s, senderId=%d, chatroomId=%d, sentOn=%s",
                id, content, sender.getId(), chatroom.getId(), date);
    }

}
