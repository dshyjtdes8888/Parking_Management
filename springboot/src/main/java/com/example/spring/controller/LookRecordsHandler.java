package com.example.spring.controller;

import com.example.spring.entity.Lookrecords;
import com.example.spring.repository.LookRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@RestController
@RequestMapping("/LookRecords")
public class LookRecordsHandler {
    @Autowired
    private LookRecordsRepository lookRecordsRepository;
    @GetMapping("/findTimes/{id}")
    public Lookrecords searchName(@PathVariable("id") int id) {
        return lookRecordsRepository.findById(id).get();
    }
}
