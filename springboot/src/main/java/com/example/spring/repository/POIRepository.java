package com.example.spring.repository;

import com.example.spring.entity.POIView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface POIRepository extends JpaRepository<POIView,Integer> {

    @Query("SELECT poi " +
            "FROM POIView poi " +
            "WHERE ST_Distance_Sphere(POINT(?1, ?2), ST_PointFromText(poi.位置)) < 2000 and poi.类别 like ?3")
    List<POIView> findKNNPark(double x, double y, String POItype);

}
