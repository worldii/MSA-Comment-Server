package com.example.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
@Getter
@AllArgsConstructor
@Builder

public class CommentLikesDto {

	String username;
	String profileUrl;
	String fullName;
}
