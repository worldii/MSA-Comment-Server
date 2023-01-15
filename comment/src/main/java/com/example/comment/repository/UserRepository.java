package com.example.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.comment.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
