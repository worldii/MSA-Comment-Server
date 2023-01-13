package com.example.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.comment.domain.Comment;
import com.example.comment.domain.CommentLikes;
import com.example.comment.repository.CommentLikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentLikesServiceImpl implements CommentLikesService {
	private final CommentLikesRepository commentLikesRepository;

	public void deleteAll(Comment comment){
		List<CommentLikes> allByComment = commentLikesRepository.findAllByComment(comment);
		commentLikesRepository.deleteAll(allByComment);
	}
}
