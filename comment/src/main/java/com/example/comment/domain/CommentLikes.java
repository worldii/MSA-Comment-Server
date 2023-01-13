package com.example.comment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommentLikes {

	@Id
	@GeneratedValue
	@Column(name="comment_likes_id")
	private long id;

	@ManyToOne(fetch =  FetchType.LAZY)
	@JoinColumn(name= "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="comment_id")
	private Comment comment;


}
