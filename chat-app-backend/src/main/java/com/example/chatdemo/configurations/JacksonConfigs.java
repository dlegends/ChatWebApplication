package com.example.chatdemo.configurations;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfigs {
    @Bean
    protected Module module() {
        return new Hibernate5Module();
    }
}
