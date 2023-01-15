package com.example.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.comment.domain.CommentMention;

@Repository
public interface CommentMentionRepository extends JpaRepository<CommentMention,Long> {
}
