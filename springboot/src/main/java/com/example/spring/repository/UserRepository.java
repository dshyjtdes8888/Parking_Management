package com.example.spring.repository;

import com.example.spring.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    List<Users> findByUsernameAndRoletypeEquals(String username, String type);

    List<Users> findByUsernameAndRoletypeAndPasswordEquals(String username, String type, String password);
    Users findByUsernameEquals(String username);
}
