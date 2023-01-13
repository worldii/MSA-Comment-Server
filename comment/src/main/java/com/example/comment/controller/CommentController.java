package com.example.comment.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.comment.domain.Comment;
import com.example.comment.dto.CommentDto;
import com.example.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@GetMapping(value = "/comments/{postId}")
	String getControllerPage(@PathVariable("postId") long postId, Model model) {
		List<Comment> allComments = commentService.findAllComments(postId);
		model.addAttribute("allComments", allComments);
		return "commentPage";
	}

	@PostMapping(value = "/comments/{postId}")
	String createComment(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto)
	{
		commentService.createComment(postId, commentDto);
		return "redirect:/comments/{postId}";
	}
	@DeleteMapping("/comments/{commentId}")
	String deleteComment(@PathVariable("commentId") long commentId){
		commentService.deleteComment(commentId);
		return "redirect:/commentPage";
	}

	@PutMapping("/comment/{commentId}")
	String updateComment(@PathVariable("commentId") long commentId, String description) {
		commentService.updateComment(commentId,description);
		return "redirect:/commentPage";
	}

	@PostMapping("/comment/like/{commentId}")
	String addCommentLikes(@PathVariable("commentId") long commentId){
		commentService.addCommentLikes(commentId);
		return null;
	}

	@PostMapping("/comment/unlike/{commentId}")
	String deleteCommentLikes(@PathVariable("commentId") long commentId){
		commentService.deleteCommentLikes(commentId);
		return null;
	}

	@PostMapping("/comment/mention/{commentId}")
	String mentionComment(@PathVariable("commmentId") long commentId) {
		commentService.mentionComment(commentId);
		return null;
	}

	@DeleteMapping("/comment/mention/{commentId}")
	String unMentionComment(@PathVariable("commentId") long commentId) {
		commentService.unMentionComment(commentId);
		return null;
	}


}

