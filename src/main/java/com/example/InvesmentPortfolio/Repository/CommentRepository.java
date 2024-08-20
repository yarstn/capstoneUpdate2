package com.example.InvesmentPortfolio.Repository;

import com.example.InvesmentPortfolio.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByAssetId(int assetId);
    List<Comment> findByParentCommentId(int parentCommentId);
    Optional<Comment> findById(Integer id);
    Comment findCommentByid(Integer id);
}



