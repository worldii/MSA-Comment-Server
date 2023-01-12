package com.example.comment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String description;
	private Long postid;

}
