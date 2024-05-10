package com.example.spring.repository;

import com.example.spring.entity.Stall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StallRepository extends JpaRepository<Stall, Integer> {
    public List<Stall> findBy小区Containing(String s);
    public Stall findByidEquals(int a);
    Page<Stall> findAllByOrderBy评分Desc(Pageable pageable);
    Page<Stall> findAllByOrderBy租金Asc(Pageable pageable);
    @Query("SELECT s FROM Stall s JOIN Lookrecords lr ON s.id = lr.id ORDER BY lr.resertimes DESC")
    Page<Stall> findAllStallsSortedByReser(Pageable pageable);

    @Query("SELECT s2 FROM Stall s2 JOIN Lookrecords lr2 ON s2.id = lr2.id ORDER BY lr2.recordtimes DESC")
    Page<Stall> findAllStallsSortedByRecord(Pageable pageable);
    public List<Stall> findBy小区ContainingAnd当前状态Containing(String s1, String s2);
    public List<Stall> findBy小区ContainingAnd租金EqualsAnd可用时间段Containing(String s1, int s3,String s2);
    public List<Stall> findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段ContainingAnd评分Between
            (String s1, String s, int price1,int price2, String time, double grade1,double grade2);
    public List<Stall> findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段Containing
            (String s1, String s, int price1,int price2, String time);
    public List<Stall> findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段ContainingAnd评分IsNull
            (String s1, String s, int price1,int price2, String time);
    public List<Stall> findBy租金EqualsAnd具体位置Containing(int num1, String num2);
}
