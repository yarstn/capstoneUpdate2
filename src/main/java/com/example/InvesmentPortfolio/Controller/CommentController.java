package com.example.InvesmentPortfolio.Controller;

import com.example.InvesmentPortfolio.Model.Comment;
import com.example.InvesmentPortfolio.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.addComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<Comment>> getCommentsByAsset(@PathVariable Integer assetId) {
        List<Comment> comments = commentService.getCommentsByAssetId(assetId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/replies/{parentCommentId}")
    public ResponseEntity<List<Comment>> getReplies(@PathVariable Integer parentCommentId) {
        List<Comment> replies = commentService.getReplies(parentCommentId);
        return ResponseEntity.ok(replies);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(200).body("Comment deleted");
    }
    @PostMapping("/reply")
    public ResponseEntity<Comment> addReply(@RequestBody Comment replyComment) {
        Comment savedReply = commentService.addReplyToComment(replyComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReply);
    }

    @GetMapping("/assets/{assetId}")
    public ResponseEntity<Map<String, Object>> getAssetComments(@PathVariable int assetId) {
        Map<String, Object> response = commentService.getAssetComments(assetId);
        return ResponseEntity.ok(response);
    }
}
