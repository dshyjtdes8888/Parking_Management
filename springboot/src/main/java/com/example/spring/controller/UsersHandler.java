package com.example.spring.controller;

import com.example.spring.entity.Users;
import com.example.spring.entity.loginUser;
import com.example.spring.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/Users")
public class UsersHandler {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/systemAdmin")
    public int systemAdminLogin(@RequestBody loginUser currentUser) {
        List<Users> list = userRepository.findByUsernameAndRoletypeEquals(currentUser.getUsername(),
                currentUser.getType());
        if (list.size() == 0) {
            return -1;
        } else {
            list = userRepository.findByUsernameAndRoletypeAndPasswordEquals(currentUser.getUsername(),
                    currentUser.getType(), currentUser.getPassword());
            if (list.size() == 0) {
                return -2;
            } else {
                return 0;
            }
        }
    }

    @PostMapping("findusername/{username}")
    public String findusername(@PathVariable("username") String username) {
        Users users=userRepository.findByUsernameEquals(username);
        if(users!=null){
            return "had";
        }
        else{
            return "no";
        }
    }
    @PostMapping("/save")
    public String save(@RequestBody Users users) {
        Users result = userRepository.save(users);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }
    }
}
