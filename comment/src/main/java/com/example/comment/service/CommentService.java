package com.example.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.comment.domain.Comment;
import com.example.comment.domain.CommentLikes;
import com.example.comment.domain.User;
import com.example.comment.dto.CommentCreateDto;
import com.example.comment.dto.CommentDto;
import com.example.comment.dto.CommentResponseData;
import com.example.comment.exception.CommentException;
import com.example.comment.exception.CommentExceptionType;
import com.example.comment.repository.CommentLikesRepository;
import com.example.comment.repository.CommentRepository;
import com.example.comment.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final CommentLikesRepository commentLikesRepository;

	// 생성
	@Transactional
	public CommentResponseData createComment(Long postId, CommentCreateDto commentCreateDto) {
		// 1. Post Service 에서 postId가 있는 지 확인. Exception 처리도 해줘야 함. (Refactoring)

		// 2. User Service 에서 user server 있는 지 확인.  Exception 처리도 해줘야 함. commentDto 의 user id 를 통해 User 를 찾고 comment 에 등록 해줌.
		// 현재 로그인 한 유저를 가져옴 (from UserService)
		// 지금은 dummy data 로 대신함.
		log.info("CreateComment 가 호출 되었습니다.");
		User tempUser = new User();
		tempUser.setId(1L);
		tempUser.setUserName("Jongha");
		tempUser.setFullName("park");
		userRepository.save(tempUser);
		Comment comment = Comment.builder()
			.user(tempUser) // 바꿔줘야 함.
			.description(commentCreateDto.getDescription())
			.postId(postId).build();
		commentRepository.save(comment);

		// 4.  Hashtag Service 에 저장.
		// 5.  mention Service 에 멘션함.
		// 5.  Alarm Service 에 보냄. (Refactor)

		CommentResponseData commentResponseData = CommentResponseData.builder()
			.id(comment.getId())
			.createdAt(comment.getCreatedAt())
			.from(comment.getUser())
			.description(comment.getDescription())
			.build();
		return commentResponseData;
	}

	@Transactional(readOnly = true)
	public List<Comment> findAllComments(Long postId) {
		return commentRepository.findAllByPostId(postId);
	}

	@Transactional
	public void deleteComment(Long commentId) {
		// 1. comment 없을 때 ERROR 처리 해줌. (ok)
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

		// 2. delete 하는 User 와 login 한 User 가 같아야 함.-> Error 처리. (User Server 관련하여 Refactoring 필요)
		// if (member != comment.getUser()) Exception 처리.
		// User tempUser = new User();
		// if (!tempUser.getId().equals(comment.getUser().getId())) {throw new RuntimeException();}

		// 3. comments Likes 다 없애야 함. (OK)
		this.deleteCommentLikesAll(comment);
		commentRepository.delete(comment);
	}

	@Transactional
	public void updateComment(Long commentId, CommentDto commentDto) {
		// 1.  comment 없을 때 (ok)
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

		// 2. 댓글 쓰려는 유저가 맞지 않을 때 -> from user server.  Refactoring 해야 함.
		// if (member != comment.getUser()) Exception 처리.
		// User tempUser = new User();
		// if (!tempUser.getId().equals(comment.getUser().getId())) {throw new RuntimeException();}

		comment.update(commentDto.getDescription());
	}

	@Transactional
	public void addCommentLikes(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

		// 1. 현재 로그인 한 유저를 가져옴 (from UserService) (Refactoring 해야 함.)
		// dummy data 로 대신함.
		User tempUser = new User();
		tempUser.setId(1L);
		tempUser.setUserName("Jongha");
		tempUser.setFullName("park");

		// 2. 에러 고치기 (refactoring), 이미 존재하니까 오류처리 해줘야 함.  (Refactoring 해야 함.)
		if (commentLikesRepository.findByUserAndComment(tempUser, comment).isPresent()) {
			throw new RuntimeException();
		}

		comment.increaseLikes();
		CommentLikes commentLikes = CommentLikes.builder().comment(comment).user(tempUser).build();
		commentLikesRepository.save(commentLikes);
		// 3. 알람 서비스 보내기. (refactoring)
	}

	@Transactional
	public void deleteCommentLikes(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

		// 1. 현재 로그인 한 유저를 가져옴 (from UserService)
		// dummy data 로 대신함.
		User tempUser = new User();
		tempUser.setId(1L);
		tempUser.setUserName("Jongha");
		tempUser.setFullName("park");

		// 2. 에러 고치기 (refactoring)
		CommentLikes commentLikes = commentLikesRepository.findByUserAndComment(tempUser, comment)
			.orElseThrow(() -> new RuntimeException());

		comment.decreaseLikes();
		commentLikesRepository.delete(commentLikes);

		// 3. 알림 보내기 ( to Alarm service) -> Refactoring
	}


	private void deleteCommentLikesAll(Comment comment) {
		List<CommentLikes> allByComment = commentLikesRepository.findAllByComment(comment);
		commentLikesRepository.deleteAll(allByComment);
	}

}
