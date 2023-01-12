package com.example.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.comment.domain.Comment;
import com.example.comment.repository.CommentRepository;

public class CommentService {
	@Autowired
	CommentRepository commentRepository;
	// 생성

	// 삭제

	// 변경

	// 조회
	// public List<Comment> findAllComments(Long postId) {
	// 	return CommentRepository.findByPostId(postId);
	// }
	//

}
