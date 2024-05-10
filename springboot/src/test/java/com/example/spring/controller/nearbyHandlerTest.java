package com.example.spring.controller;

import com.example.spring.entity.nearby;
import com.example.spring.entity.stall_geo;
import com.example.spring.repository.NearbyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class nearbyHandlerTest {
    @Autowired
    private NearbyRepository nearbyRepository;

    @Test
    void findAll() {
        System.out.println(nearbyRepository.findAll());
        List<nearby> list = nearbyRepository.findAll();
        List<stall_geo> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            stall_geo tmp = new stall_geo();
            tmp.setS_id(list.get(i).getSid());
            tmp.setN_id(list.get(i).getNid());
            tmp.set小区(list.get(i).get小区());
            String s = list.get(i).get位置();
            s = s.replaceAll("[a-zA-Z]", "");
            s = s.replaceAll("\\(|\\)", "");
            String[] strings = s.split(" ");
            tmp.setX(Double.parseDouble(strings[0]));
            tmp.setY(Double.parseDouble(strings[1]));
            list1.add(tmp);
        }
        System.out.println(list1);
    }
    @Test
    void findKnnStall(){
        System.out.println(nearbyRepository.findKNNStall(114.414081085,30.5008674171));
    }
}