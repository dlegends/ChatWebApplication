package com.example.chatdemo.serializers;

import com.example.chatdemo.entities.Chatroom;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomChatroomSerializer extends StdSerializer<Chatroom> {
    protected CustomChatroomSerializer(Class<Chatroom> t) {
        super(t);
    }

    public CustomChatroomSerializer() {
        this(null);
    }

    @Override
    public void serialize(
            Chatroom chatroom, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(chatroom.getId());
    }
}
