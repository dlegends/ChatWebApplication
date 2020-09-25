package com.example.chatdemo.entities;

import com.example.chatdemo.serializers.CustomChatroomSetSerializer;
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
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    Set<Role> roles;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonSerialize(using = CustomChatroomSetSerializer.class)
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "participants"
    )
    Set<Chatroom> joinedChatrooms = new HashSet<>();

    public void removeFromChatroom(Chatroom chatroom) {
        joinedChatrooms.remove(chatroom);
    }
}
