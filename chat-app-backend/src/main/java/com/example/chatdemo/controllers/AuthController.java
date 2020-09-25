package com.example.chatdemo.controllers;

import com.example.chatdemo.ApiResponse;
import com.example.chatdemo.JwtData;
import com.example.chatdemo.LoginForm;
import com.example.chatdemo.RegisterForm;
import com.example.chatdemo.entities.User;
import com.example.chatdemo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

//    @Autowired
//    RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public JwtData authenticateUser(@RequestBody LoginForm loginForm) {
        return authService.loginUser(loginForm);
    }

    @PostMapping("/refreshToken")
    public JwtData refreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String jwt = null;
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            jwt =  bearerToken.substring(7, bearerToken.length());
        }
        return authService.refreshToken(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterForm registerForm) {
        User user = authService.registerUser(registerForm);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered Successfully"));
    }
}
