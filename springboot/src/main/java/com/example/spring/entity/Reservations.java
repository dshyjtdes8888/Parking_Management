package com.example.spring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer sid;
    private String 小区;
    private String customerName;
    private String phonenumber;
    private String date;
    private String begintime;
    private String endtime;
    private String state;
}
