package com.example.chatdemo.serializers;

import com.example.chatdemo.entities.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomUserSetSerializer extends StdSerializer<Set<User>> {
    public CustomUserSetSerializer(Class<Set<User>> t) {
        super(t);
    }

    public CustomUserSetSerializer() {
        this(null);
    }
    @Override
    public void serialize(
            Set<User> ts, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        Set<Long> ids = new HashSet<>();
        for (User user: ts) {
            ids.add(user.getId());
        }
        jsonGenerator.writeObject(ids);
    }
}
