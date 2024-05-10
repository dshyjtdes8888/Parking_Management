package com.example.spring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Lookrecords {
    @Id
    private Integer id;
    private Integer recordtimes;
    private Integer resertimes;
}
