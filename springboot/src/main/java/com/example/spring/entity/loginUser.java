package com.example.spring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class loginUser {
    @Id
    private String username;
    private String password;
    private String usertype;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return usertype;
    }

    public void setType(String type) {
        this.usertype = type;
    }
}

