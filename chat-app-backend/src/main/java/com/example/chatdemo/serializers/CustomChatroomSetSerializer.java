package com.example.chatdemo.serializers;

import com.example.chatdemo.entities.Chatroom;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomChatroomSetSerializer extends StdSerializer<Set<Chatroom>> {
    protected CustomChatroomSetSerializer(Class<Set<Chatroom>> t) {
        super(t);
    }

    public CustomChatroomSetSerializer() {
        this(null);
    }
    @Override
    public void serialize(
            Set<Chatroom> chatrooms, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        Set<Long> ids = new HashSet<>();
        for (Chatroom chatroom: chatrooms) {
            ids.add(chatroom.getId());
        }
        jsonGenerator.writeObject(ids);
    }
}
