package com.example.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.comment.domain.Comment;
import com.example.comment.domain.CommentLikes;
import com.example.comment.dto.CommentCreateDto;
import com.example.comment.dto.CommentDto;
import com.example.comment.dto.CommentResponseData;
import com.example.comment.exception.CustomException;
import com.example.comment.exception.ErrorCode;
import com.example.comment.repository.CommentLikesRepository;
import com.example.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
	private final CommentRepository commentRepository;
	private final CommentLikesRepository commentLikesRepository;
	private final CommentMentionService commentMentionService;

	@Transactional(readOnly = true)
	public List<Comment> findAllComments(Long postId) {
		return commentRepository.findAllByPostId(postId);
	}

	@Transactional
	public CommentResponseData createComment(Long postId, CommentCreateDto commentCreateDto) {
		// Notification Service 에 보냄. -> Refactoring
		log.info("comment create");

		Comment comment = Comment.builder()
			.userId(commentCreateDto.getUserId())
			.description(commentCreateDto.getDescription())
			.postId(postId).build();
		commentRepository.save(comment);

		log.info("Mention Someone");
		commentMentionService.mentionMember(commentCreateDto.getUserId(), commentCreateDto);
		CommentResponseData commentResponseData = CommentResponseData.builder()
			.id(comment.getId())
			.createdAt(comment.getCreatedAt())
			.userId(comment.getUserId())
			.description(comment.getDescription())
			.build();

		return commentResponseData;
	}

	@Transactional
	public void deleteComment(Long commentId) {
		// Refactoring
		// delete하는 comment의 User와 login 한 User 가 같아야 함.-> Error 처리. (User Server)
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

		this.deleteCommentLikesAll(commentId);
		commentMentionService.deleteMentionAll(commentId);
		commentRepository.delete(comment);
	}

	@Transactional
	public void updateComment(Long commentId, CommentDto commentDto) {
		// Refactoring
		// 댓글 쓰려는 유저가 맞지 않을 때 -> from user server.
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));
		comment.update(commentDto.getDescription());
	}

	@Transactional
	public void addCommentLikes(Long commentId, Long userId) {
		// Refactoring 필요
		// 1. 현재 로그인 한 유저를 가져옴 (from UserService) (Refactoring 해야 함.)
		// 2. Notification 서비스 보내기. ( Notification refactoring)

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));
		if( commentLikesRepository.findByUserIdAndCommentId(userId, commentId).isPresent())
		{
			throw new CustomException(ErrorCode.COMMENT_ALREADY_EXIST);
		}

		comment.increaseLikes();
		CommentLikes commentLikes = CommentLikes.builder().commentId(commentId).userId(userId).build();
		commentLikesRepository.save(commentLikes);
	}

	@Transactional
	public void deleteCommentLikes(Long commentId, Long userId) {
		// for Refactoring
		// 1. 현재 로그인 한 유저를 가져옴 (from UserService)
		// 2. Notification 보내기 ( to Notification service) -> Refactoring

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

		CommentLikes commentLikes = commentLikesRepository.findByUserIdAndCommentId(userId, commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

		comment.decreaseLikes();
		commentLikesRepository.delete(commentLikes);
	}

	@Transactional
	public List<CommentLikes> getCommentLikesList(long commentId) {
		List<CommentLikes> allByCommentId = commentLikesRepository.findAllByCommentId(commentId);
		log.info("좋아요 리스트 " + allByCommentId);
		return allByCommentId;
	}

	@Transactional
	public boolean checkCommentIsLikedByUser(long commentId, long userId) {
		boolean present = commentLikesRepository.findByUserIdAndCommentId(userId, commentId).isPresent();
		log.info("좋아요 여부 "+ present);
		return present;
	}

	private void deleteCommentLikesAll(Long commentId) {
		List<CommentLikes> allByComment = commentLikesRepository.findAllByCommentId(commentId);
		commentLikesRepository.deleteAll(allByComment);
	}
}
