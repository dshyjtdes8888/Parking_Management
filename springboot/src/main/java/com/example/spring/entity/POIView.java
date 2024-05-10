package com.example.spring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class POIView {
    @Id
    private Integer id;
    private String name;
    private String 类别;
    private String 电话;
    private String 位置;
    private String text位置;
}
