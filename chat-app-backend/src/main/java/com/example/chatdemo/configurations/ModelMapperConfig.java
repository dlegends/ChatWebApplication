package com.example.chatdemo.configurations;

import com.example.chatdemo.entities.Chatroom;
import com.example.chatdemo.entities.ChatroomDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(new PropertyMap<Chatroom, ChatroomDTO>() {
            @Override
            protected void configure() {
                skip(destination.getChatMessages());
                skip(destination.getParticipants());
            }
        });
        return modelMapper;
    }
}
