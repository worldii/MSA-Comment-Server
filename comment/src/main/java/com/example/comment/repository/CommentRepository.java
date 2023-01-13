package com.example.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.comment.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long > {
	List<Comment> findAllByPostId(Long postId) ;
}
