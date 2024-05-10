package com.example.spring.controller;

import com.example.spring.entity.Lookrecords;
import com.example.spring.entity.Reservations;
import com.example.spring.entity.Stall;
import com.example.spring.repository.LookRecordsRepository;
import com.example.spring.repository.ReservationsRepository;
import com.example.spring.repository.StallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/Reservations")
public class ReservationsHandler {
    @Autowired
    private ReservationsRepository reserRepository;
    @Autowired
    private StallRepository stallRepository;
    @Autowired
    private LookRecordsRepository lookRecordsRepository;

    @PostMapping("/save")
    public String save(@RequestBody Reservations reser) {
        reser.setState("已预约");
        String s = reser.getDate().substring(0,10);
        reser.setDate(s);
        Reservations result = reserRepository.save(reser);
        //更新车位表
        Stall stall=stallRepository.findByidEquals(reser.getSid());
        stall.set当前状态("已预约");
        stallRepository.save(stall);
        return "success";
    }


    @GetMapping("/findAll/{page}/{size}")
    public Page<Reservations> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return reserRepository.findAll(pageable);
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        Reservations reser=reserRepository.findByidEquals(id);
        Stall stall=stallRepository.findByidEquals(reser.getSid());
        stall.set当前状态("空闲中");
        stallRepository.save(stall);
        reserRepository.deleteById(id);
    }

    @PostMapping("/update")
    public String update(@RequestBody Reservations reser) {
        reser.setState("结束");
        Reservations result = reserRepository.save(reser);
        Stall stall=stallRepository.findByidEquals(reser.getSid());
        stall.set当前状态("空闲中");
        stallRepository.save(stall);
        //更新预约次数
        Lookrecords lookrecords=lookRecordsRepository.findByidEquals(reser.getSid());
        lookrecords.setResertimes(lookrecords.getResertimes() + 1);
        lookRecordsRepository.save(lookrecords);
        return "success";
    }

    @PostMapping("/cancel")
    public String cancel(@RequestBody Reservations reser) {
        reser.setState("已取消");
        Reservations result = reserRepository.save(reser);
        Stall stall=stallRepository.findByidEquals(reser.getSid());
        stall.set当前状态("空闲中");
        stallRepository.save(stall);
        return "success";
    }

    @PostMapping("/run")
    public String run(@RequestBody Reservations reser) {
        reser.setState("使用中");
        Reservations result = reserRepository.save(reser);
        Stall stall=stallRepository.findByidEquals(reser.getSid());
        stall.set当前状态("使用中");
        stallRepository.save(stall);
        return "success";
    }
}
