package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.Comment;
import com.example.newsportalmegacomproject.db.model.ReplyComment;
import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.CommentRepository;
import com.example.newsportalmegacomproject.db.repository.ReplyCommentRepository;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.CommentRequest;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import com.example.newsportalmegacomproject.dto.response.CommentedUserResponse;
import com.example.newsportalmegacomproject.dto.response.ReplyCommentResponse;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyToCommentService {

    private final ReplyCommentRepository replyCommentRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByNickName(login).orElseThrow(
                () -> new NotFoundException("User with nick name: " + login + " not found!")
        );
    }

    public CommentResponse replyComment(CommentRequest request) {
        User user = getAuthenticateUser();
        Comment comment = commentRepository.findById(request.getId()).orElseThrow(
                () -> new NotFoundException("Comment with id: " + request.getId() + " not found!")
        );

        ReplyComment replyComment = new ReplyComment(request.getText());
        replyComment.setComment(comment);
        comment.addReplyComment(replyComment);
        replyComment.setUser(user);
        replyCommentRepository.save(replyComment);
        CommentedUserResponse userResponse = userRepository.getCommentedUser(comment.getUser().getId());
        List<ReplyCommentResponse> replyCommentResponses = new ArrayList<>();
        for (ReplyComment com : comment.getReplyComments()) {
            CommentedUserResponse commentedUserResponse = userRepository.getCommentedUser(com.getUser().getId());
            ReplyCommentResponse replyCommentResponse = new ReplyCommentResponse(com, commentedUserResponse);
            replyCommentResponses.add(replyCommentResponse);
        }

        return new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                userResponse,
                replyCommentResponses
        );
    }

    public List<ReplyCommentResponse> getAllReplyComments(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Comment with id: " + id + " not found!")
        );

        return replyCommentRepository.getAllReplyCommentResponse(comment.getId());
    }
}