package com.example.spring.repository;

import com.example.spring.entity.Reservations;
import com.example.spring.entity.Stall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {
    public Reservations findByidEquals(int a);

}
