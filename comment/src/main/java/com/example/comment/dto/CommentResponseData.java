package com.example.comment.dto;

import java.time.LocalDateTime;

import com.example.comment.domain.User;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class CommentResponseData {
	private Long id;
	private User from;
	private String description;
	private LocalDateTime createdAt;
}
