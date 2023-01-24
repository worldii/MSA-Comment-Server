package com.example.comment.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.comment.domain.Comment;
import com.example.comment.domain.CommentLikes;
import com.example.comment.dto.CommentCreateDto;
import com.example.comment.dto.CommentDto;
import com.example.comment.dto.CommentLikesDto;
import com.example.comment.dto.CommentResponseData;
import com.example.comment.dto.ResultCode;
import com.example.comment.dto.ResultResponse;
import com.example.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
	private final CommentService commentService;

	@GetMapping("/comments/{postId}")
	public ResponseEntity<ResultResponse> getComments(@PathVariable Long postId) {
		List<Comment> allComments = commentService.findAllComments(postId);
		for (int i = 0; i < allComments.size(); i++) {
			log.info("description" + allComments.get(i).getUser().getUserName());
		}
		List<CommentDto> collect = allComments.stream()
			.map(comment -> new CommentDto(comment.getId(),comment.getUser().getUserName(), comment.getUser().getProfilePicture(), comment.getDescription(),
				comment.getLikes()))
			.collect(
				Collectors.toList());
		return ResponseEntity.ok(new ResultResponse(ResultCode.GET_COMMENT_PAGE_SUCCESS, collect));
	}

	@PostMapping(value = "/comments/{postId}")
	public ResponseEntity<ResultResponse> createComment(@PathVariable("postId") Long postId,
		@RequestBody @Valid CommentCreateDto commentCreateDto) {
		CommentResponseData responseData = commentService.createComment(postId, commentCreateDto);
		return ResponseEntity.ok(new ResultResponse(ResultCode.CREATE_COMMENT_SUCCESS, responseData));
	}

	@DeleteMapping("/comments/{commentId}")
	ResponseEntity<ResultResponse> deleteComment(@PathVariable("commentId") Long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.ok(new ResultResponse(ResultCode.DELETE_COMMENT_SUCCESS, ""));
	}

	@PutMapping("/comments/{commentId}")
	ResponseEntity<ResultResponse> updateComment(@PathVariable("commentId") Long commentId,
		@RequestBody @Valid CommentDto commentDto) {
		commentService.updateComment(commentId, commentDto);
		return ResponseEntity.ok(new ResultResponse(ResultCode.UPDATE_COMMENT_SUCCESS, ""));
	}

	@PostMapping("/comments/like/{commentId}")
	ResponseEntity<ResultResponse> addCommentLikes(@PathVariable("commentId") long commentId) {
		commentService.addCommentLikes(commentId);
		return ResponseEntity.ok(new ResultResponse(ResultCode.LIKE_COMMENT_SUCCESS, ""));
	}

	@PostMapping("/comments/unlike/{commentId}")
	ResponseEntity<ResultResponse> deleteCommentLikes(@PathVariable("commentId") long commentId) {
		commentService.deleteCommentLikes(commentId);
		return ResponseEntity.ok(new ResultResponse(ResultCode.UNLIKE_COMMENT_SUCCESS, ""));
	}

	@GetMapping("/comments/like/{commentId}")
	ResponseEntity<ResultResponse> getCommentLikesList(@PathVariable("commentId") long commentId) {
		List<CommentLikes> commentLikesList = commentService.getCommentLikesList(commentId);
		List<CommentLikesDto> collect = commentLikesList.stream()
			.map(comment -> new CommentLikesDto(comment.getUser().getUserName(), comment.getUser().getProfilePicture(),
				comment.getUser().getFullName()))
				.collect(
					Collectors.toList());

		return ResponseEntity.ok(new ResultResponse(ResultCode.GET_COMMENT_LIKES_SUCCESS, collect));
	}

	@GetMapping("/comments/isLiked/{commentId}")
	ResponseEntity<ResultResponse> getCommentisLikedByUser(@PathVariable ("commentId") long commentId)
	{
		boolean result = commentService.checkCommentIsLikedByUser(commentId);
		return ResponseEntity.ok(new ResultResponse(ResultCode.GET_COMMENT_IS_LIKED_SUCCESS,result));
	}
}
