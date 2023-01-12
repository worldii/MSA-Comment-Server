package com.example.comment.service;

import java.util.List;

import com.example.comment.domain.Comment;
import com.example.comment.dto.CommentDto;



public interface CommentService {
	void createComment(Long postId, Comment comment);

	void updateComment(Long commentId, String description);

	void deleteComment(Long commentId);

	List<Comment> findAllComments(Long postId);
}