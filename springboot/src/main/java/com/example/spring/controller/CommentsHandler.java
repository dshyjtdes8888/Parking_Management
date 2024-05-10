package com.example.spring.controller;

import com.example.spring.entity.Comment;
import com.example.spring.entity.Comments;
import com.example.spring.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RestController
@RequestMapping("/Comments")
class CommentsHandler {
    @Autowired
    private CommentsRepository commentsRepository;

    @PostMapping("/save")
    public String save(@RequestBody Comment comment) {
        Comments comments = new Comments();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String s = formatter.format(date);
        comments.setDate(s);
        comments.setSid(comment.getId());
        comments.setComment(comment.getTextarea());
        Comments result = commentsRepository.save(comments);
        return "success";
    }

    @GetMapping("/findComment/{id}")
    public List<Comments> findAllComments(@PathVariable("id") Integer id) {
        return commentsRepository.findBySid(id);
    }
}
