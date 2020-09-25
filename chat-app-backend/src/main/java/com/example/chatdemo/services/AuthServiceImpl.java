package com.example.chatdemo.services;

import com.example.chatdemo.JwtData;
import com.example.chatdemo.LoginForm;
import com.example.chatdemo.RegisterForm;
import com.example.chatdemo.entities.User;
import com.example.chatdemo.exceptions.RegisteredUsernameException;
import com.example.chatdemo.repositories.UserRepository;
import com.example.chatdemo.security.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtils tokenUtils;

    @Override
    public User registerUser(RegisterForm registerForm) {
        final String username = registerForm.getUsername();
        final String password = registerForm.getPassword();

        userRepository.findByUsername(username)
                .ifPresent(user -> {throw new RegisteredUsernameException(username);});

        // Creating user's account
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public JwtData loginUser(LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getUsername(),
                        loginForm.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenUtils.generateToken(authentication);

        return tokenUtils.generateJwtData(authentication);
    }

    @Override
    public JwtData refreshToken(String jwt) {
        return tokenUtils.refreshToken(jwt);
    }
}
