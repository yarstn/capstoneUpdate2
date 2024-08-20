package com.example.InvesmentPortfolio.Service;

import com.example.InvesmentPortfolio.Model.Assest;
import com.example.InvesmentPortfolio.Model.Comment;
import com.example.InvesmentPortfolio.Model.User;
import com.example.InvesmentPortfolio.Repository.AssestRepository;
import com.example.InvesmentPortfolio.Repository.CommentRepository;
import com.example.InvesmentPortfolio.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final AssestRepository assestRepository;
    private final UserRepository userRepository;

    public Comment addComment(Comment comment) {
        // Fetch the asset to ensure it exists
        Assest asset = assestRepository.findByid(comment.getAssetId());
        if (asset == null) {
            throw new RuntimeException("Asset with ID " + comment.getAssetId() + " not found");
        }

        // Set the created date for the comment
        comment.setCreatedDate(LocalDateTime.now());

        // Save the comment to the database
        Comment savedComment = commentRepository.save(comment);

        // Log or print debugging information
        System.out.println("Comment added successfully: " + savedComment);

        return savedComment;
    }

    public List<Comment> getCommentsByAssetId(Integer assetId) {
        return commentRepository.findByAssetId(assetId);
    }

    public List<Comment> getReplies(Integer parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId);
    }
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findCommentByid(commentId);
        commentRepository.delete(comment);
    }

    public Comment addReplyToComment(Comment replyComment) {
        // Log or print the IDs for debugging
        System.out.println("Parent Comment ID: " + replyComment.getParentCommentId());
        System.out.println("User ID: " + replyComment.getUserId());

        // Check if parent comment exists
        Comment parentComment = commentRepository.findById(replyComment.getParentCommentId())
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        // Check if user exists
        User user = userRepository.findById(replyComment.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        replyComment.setCreatedDate(LocalDateTime.now());
        return commentRepository.save(replyComment);
    }
    public Map<String, Object> getAssetComments(int assetId) {
        List<Comment> comments = commentRepository.findByAssetId(assetId);
        Map<Integer, List<Comment>> repliesMap = new HashMap<>();
        List<Map<String, Object>> commentsWithReplies = new ArrayList<>();

        // Build the replies map
        for (Comment comment : comments) {
            if (comment.getParentCommentId() > 0) {
                repliesMap.computeIfAbsent(comment.getParentCommentId(), k -> new ArrayList<>()).add(comment);
            }
        }

        // Process comments and replies
        for (Comment comment : comments) {
            if (comment.getParentCommentId() == 0) {
                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("id", comment.getId());
                commentMap.put("content", comment.getContent());
                commentMap.put("userId", comment.getUserId());
                commentMap.put("createdDate", comment.getCreatedDate());

                List<Map<String, Object>> replies = new ArrayList<>();
                List<Comment> childComments = repliesMap.getOrDefault(comment.getId(), Collections.emptyList());
                for (Comment reply : childComments) {
                    Map<String, Object> replyMap = new HashMap<>();
                    replyMap.put("id", reply.getId());
                    replyMap.put("content", reply.getContent());
                    replyMap.put("userId", reply.getUserId());
                    replyMap.put("createdDate", reply.getCreatedDate());
                    replies.add(replyMap);
                }

                commentMap.put("replies", replies);
                commentsWithReplies.add(commentMap);
            }
        }

        return Map.of(
                "assetId", assetId,
                "comments", commentsWithReplies
        );
    }
}
