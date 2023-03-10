package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.Comment;
import com.example.newsportalmegacomproject.db.model.News;
import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.CommentRepository;
import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.db.repository.ReplyCommentRepository;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.CommentRequest;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import com.example.newsportalmegacomproject.dto.response.CommentedUserResponse;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final ReplyCommentRepository replyCommentRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByNickName(login).orElseThrow(
                () -> new NotFoundException("User with nick name: " + login + " not found!")
        );
    }

    public CommentResponse addCommentToNews(CommentRequest request) {
        User user = getAuthenticateUser();
        News news = newsRepository.findById(request.getId()).orElseThrow(
                () -> new NotFoundException("News with id: " + request.getId() + " not found!")
        );

        Comment comment = new Comment(request);
        comment.setNews(news);
        comment.setUser(user);
        user.addComment(comment);
        Comment save = commentRepository.save(comment);
        CommentedUserResponse userResponse = userRepository.getCommentedUser(save.getUser().getId());
        return new CommentResponse(
                save.getId(),
                save.getText(),
                save.getCreatedAt(),
                userResponse,
                new ArrayList<>()
        );
    }
}
