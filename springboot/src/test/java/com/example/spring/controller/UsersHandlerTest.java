package com.example.spring.controller;

import com.example.spring.entity.loginUser;
import com.example.spring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UsersHandlerTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByuser() {
        System.out.println(userRepository.findByUsernameAndRoletypeEquals
                ("aaa", "systemAdmin"));
        System.out.println(userRepository.findByUsernameAndRoletypeAndPasswordEquals
                ("aaa", "systemAdmin", "a1234567"));
        System.out.println(userRepository.findByUsernameEquals
                ("bbb"));

    }


}