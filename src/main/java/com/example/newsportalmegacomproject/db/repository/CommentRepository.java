package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.Comment;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select new com.example.newsportalmegacomproject.dto.response.CommentResponse(" +
            "c.id, " +
            "c.text, " +
            "c.commentedDate) from Comment c where c.id = :id")
    CommentResponse getCommentResponse(Long id);

    @Query("select c from Comment c where c.news.id = :newsId")
    List<Comment> getAllNewsComment(Long newsId);
}