package com.example.spring.repository;

import com.example.spring.entity.stall_geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface stall_geoRepository extends JpaRepository<stall_geo, Integer> {
    public stall_geo findBySid(int sid);
}
