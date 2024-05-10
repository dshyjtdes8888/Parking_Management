package com.example.spring.controller;

import com.example.spring.entity.POIView;
import com.example.spring.repository.POIRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class POIViewHandlerTest {
    @Autowired
    private POIRepository poiRepository;

    @Test
    void findKNNParkTest() {
        List<POIView> pois = poiRepository.findKNNPark(114.414081085, 30.5008674171, "%厕所%");
        System.out.println(pois);
    }
}