package com.example.security.service;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public List<User> allUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
