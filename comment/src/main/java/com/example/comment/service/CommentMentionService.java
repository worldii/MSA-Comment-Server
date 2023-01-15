package com.example.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.comment.repository.CommentMentionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentMentionService {
	private final CommentMentionRepository commentMentionRepository;

	@Transactional
	public void mentionMember(){}

	@Transactional
	public void deleteMentionAll(){

	}
}
