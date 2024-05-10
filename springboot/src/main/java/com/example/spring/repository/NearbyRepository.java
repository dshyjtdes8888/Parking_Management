package com.example.spring.repository;

import com.example.spring.entity.nearby;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NearbyRepository extends JpaRepository<nearby,Integer> {
    // "SELECT poi FROM POIView poi WHERE ST_Distance_Sphere(POINT(?1, ?2), poi.位置) < 2000 and poi.类别 = ?3"
    public nearby findBySidEquals(int a);
    @Query(value = "SELECT n " +
            "FROM nearby n " +
            "WHERE ST_Distance_Sphere(POINT(?1, ?2), ST_PointFromText(n.位置)) < 2000")
    List<nearby> findKNNStall(double x, double y);

}
