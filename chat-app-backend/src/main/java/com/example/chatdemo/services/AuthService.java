package com.example.chatdemo.services;

import com.example.chatdemo.JwtData;
import com.example.chatdemo.LoginForm;
import com.example.chatdemo.RegisterForm;
import com.example.chatdemo.entities.User;

public interface AuthService {
    User registerUser(RegisterForm registerForm);

    /**
     * Return the jwt token for valid login information
     * @param loginForm information required for login
     * @return jwt token for the user matching information of the loginForm
     */
    JwtData loginUser(LoginForm loginForm);
    JwtData refreshToken(String jwt);
}
