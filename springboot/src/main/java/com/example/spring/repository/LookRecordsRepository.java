package com.example.spring.repository;

import com.example.spring.entity.Lookrecords;
import com.example.spring.entity.Stall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LookRecordsRepository extends JpaRepository<Lookrecords, Integer> {
    public Lookrecords findByidEquals(int a);
}
