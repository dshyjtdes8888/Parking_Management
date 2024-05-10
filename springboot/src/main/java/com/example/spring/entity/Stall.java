package com.example.spring.entity;


import lombok.Data;

import javax.persistence.*;

// 类名和表名的映射
@Entity
@Data
public class Stall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String 小区;
    private Integer 租金;
    private String 当前状态;
    private String 可用时间段;
    private String 具体位置;
    private String 联系电话;
    private Double 评分;
    private String 图片;
}

