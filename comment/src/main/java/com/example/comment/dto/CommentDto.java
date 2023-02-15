package com.example.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
	private Long id;
	private Long userId;
	private String userName;
	private String ProfileUrl;
	private String description;
	private int likes;
}
