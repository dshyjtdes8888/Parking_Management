package com.example.spring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class POIjson {
    @Id
    private Integer id;
    private String name;
    private String 类别;
    private double x;
    private double y;
    private String 电话;
    private String text位置;
}
