package com.example.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder

public class CommentLikesDto {

	String username;
	String profileUrl;
	String fullName;
}
