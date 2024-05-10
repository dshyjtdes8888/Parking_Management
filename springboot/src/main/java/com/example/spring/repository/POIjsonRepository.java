package com.example.spring.repository;


import com.example.spring.entity.POIjson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface POIjsonRepository extends JpaRepository<POIjson,Integer> {
    public POIjson findByIdEquals(int a);
}
