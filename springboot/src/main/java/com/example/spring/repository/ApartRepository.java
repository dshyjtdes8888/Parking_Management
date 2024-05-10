package com.example.spring.repository;

import com.example.spring.entity.Apart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApartRepository extends JpaRepository<Apart, Integer> {
    @Query(value = "SELECT a FROM Apart a WHERE a.小区 = ?1")
    Apart findBy小区(String 小区);
}
