package com.example.comment.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDto {
	@JsonProperty(required = true)
	private Long userId;

	private String userName;
	private String profileUrl;
	private String description;
}
