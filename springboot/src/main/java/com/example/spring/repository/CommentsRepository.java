package com.example.spring.repository;

import com.example.spring.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    public List<Comments> findBySid(int id);
}
