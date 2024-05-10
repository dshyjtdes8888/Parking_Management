package com.example.spring.controller;

import com.example.spring.repository.CommentsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class CommentsHandlerTest {
    @Autowired
    private CommentsRepository commentsRepository;
    @Test
    void findAll(){
        System.out.println(commentsRepository.findAll());
    }
    @Test
    void getTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
    }
}
