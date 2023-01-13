package com.example.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.comment.domain.Comment;
import com.example.comment.domain.CommentLikes;
import com.example.comment.domain.User;

@Repository
public interface CommentLikesRepository  extends JpaRepository<CommentLikes, Long> {
	Optional<CommentLikes> findByUserAndComment(User user, Comment comment);
	List<CommentLikes> findAllByComment(Comment comment);
}
