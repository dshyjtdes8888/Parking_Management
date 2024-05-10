package com.example.spring.controller;

import com.example.spring.repository.LookRecordsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LookRecordsHandlerTest {
    @Autowired
    private LookRecordsRepository lookRecordsRepository;
    @Test
    void test(){
        System.out.println(lookRecordsRepository.findAll());
    }
}