package com.example.spring.controller;

import com.example.spring.entity.Stall;
import com.example.spring.repository.StallRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class StallHandlerTest {
    @Autowired
    private StallRepository stallRepository;


    @Test
    void findAllByOrder() {
        System.out.println(stallRepository.findAllByOrderBy评分Desc(Pageable.ofSize(2)));
        System.out.println(stallRepository.findAllByOrderBy租金Asc(Pageable.ofSize(2)));
        System.out.println(stallRepository.findAllStallsSortedByRecord(Pageable.ofSize(2)));
        System.out.println(stallRepository.findAllStallsSortedByReser(Pageable.ofSize(2)));
    }

    @Test
    void findMultiple() {
        System.out.println(stallRepository.findBy小区ContainingAnd当前状态Containing("泷","空闲中"));
        System.out.println(stallRepository.findBy小区ContainingAnd租金EqualsAnd可用时间段Containing("泷",5,"8:00-24:00"));
        System.out.println(stallRepository.findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段ContainingAnd评分Between
                ("泷","空闲中",0,20,"8:00-24:00",0,5));
        System.out.println(stallRepository.findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段ContainingAnd评分IsNull
                ("泷","空闲中",0,20,"8:00-24:00"));
        System.out.println(stallRepository.findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段Containing
                ("泷","空闲中",0,20,"8:00-24:00"));
    }
}
