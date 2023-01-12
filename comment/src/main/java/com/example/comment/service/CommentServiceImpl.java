package com.example.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.comment.domain.Comment;
import com.example.comment.exception.CommentException;
import com.example.comment.exception.CommentExceptionType;
import com.example.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

// NOT IMPLEMENT -> DTO 로 바꾸어 주어야 함
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	private final CommentRepository commentRepository;
	// 생성
	@Override
	public void  createComment(Long postId , Comment comment){
		// post id 있는 지 확인 !
		// post 서버에서 확인해야 할 듯 -> Exception 처리도 해줘야 함.
		// NOT IMPLEMENT
		// user server 확인
		comment.setPostId(postId);
		commentRepository.save(comment);
	}
	// 삭제
	@Override
	public void deleteComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));
		commentRepository.delete(comment);
	}

	// 변경
	@Override
	public void updateComment(Long commentId, String description){
		// comment 없을 때
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));
		// 댓글 쓰려는 유저가 맞지 않을 때 -> user server 랑 소통 NOT IMPLEMENT
		comment.setDescription(description);
	}

	// 조회
	@Override
	@Transactional(readOnly = true)
	public List<Comment> findAllComments(Long postId) {
		return commentRepository.findByPostId(postId);
	}
}
