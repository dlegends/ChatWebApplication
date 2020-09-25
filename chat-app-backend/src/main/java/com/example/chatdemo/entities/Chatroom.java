package com.example.chatdemo.entities;

import com.example.chatdemo.serializers.CustomUserSetSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Chatroom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatroom")
    Set<ChatMessage> chatMessages = new HashSet<>();

    // The json representation of participants is set of integers,
    // each representing ID of user (to avoid infinite json recursion)

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "chatrooms_join",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chatroom_id")
    )
    Set<User> participants = new HashSet<>();
}