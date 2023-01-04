package com.example.newsportalmegacomproject.api;

import com.example.newsportalmegacomproject.db.service.ReplyToCommentService;
import com.example.newsportalmegacomproject.dto.request.ReplyToCommentRequest;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reply-comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Reply comment API", description = "All endpoints of reply comments")
public class ReplyToCommentAPI {

    private final ReplyToCommentService replyToCommentService;

    @Operation(summary = "Reply to comment", description = "Reply to comment by comment id")
    @PostMapping("/{commentId}")
    public CommentResponse replyComment(@PathVariable Long commentId,
                                        @RequestBody ReplyToCommentRequest request) {
        return replyToCommentService.replyComment(commentId, request);
    }
}