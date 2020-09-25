package com.example.chatdemo.services;

import com.example.chatdemo.LoginForm;
import com.example.chatdemo.RegisterForm;
import com.example.chatdemo.entities.ChatroomDTO;
import com.example.chatdemo.entities.User;
import com.example.chatdemo.entities.UserDTO;

import java.util.Set;

public interface UserService {
    Set<ChatroomDTO> getJoinedChatrooms(Long userId);
}
