package com.example.spring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class nearby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nid;
    private Integer sid;
    private String 小区;
    private String 位置;
}
