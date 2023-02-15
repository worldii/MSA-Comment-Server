package com.example.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.comment.domain.Comment;
import com.example.comment.domain.CommentMention;

@Repository
public interface CommentMentionRepository extends JpaRepository<CommentMention, Long> {
	List<CommentMention> findAllByCommentId(Long commentId);
}
