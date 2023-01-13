package com.example.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.comment.domain.Comment;
import com.example.comment.domain.CommentLikes;
import com.example.comment.domain.User;
import com.example.comment.dto.CommentDto;
import com.example.comment.exception.CommentException;
import com.example.comment.exception.CommentExceptionType;
import com.example.comment.repository.CommentLikesRepository;
import com.example.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	private final CommentRepository commentRepository;
	private final CommentLikesRepository commentLikesRepository;
	private final CommentLikesService commentLikesService;

	// 생성
	@Override
	public void  createComment(Long postId, CommentDto commentDto){

		// 1. Post Service 에서 postId가 있는 지 확인. Exception 처리도 해줘야 함. (Refactoring)

		// 2. User Service 에서 user server 있는 지 확인.  Exception 처리도 해줘야 함. commentDto 의 user id 를 통해 User 를 찾고 comment 에 등록 해줌.
		// 현재 로그인 한 유저를 가져옴 (from UserService)
		// 지금은 dummy data 로 대신함.
		User tempUser = new User();
		tempUser.setId(1L);
		tempUser.setUserName("Jongha");
		tempUser.setFullName("park");
		Comment comment = new Comment();
		comment.setUser(tempUser);

		// 3. Refactoring 시에 commentDto 로 바꾸어 주어야 함. (Refactoring)
		comment.setDescription(commentDto.getDescription());
		comment.setId(commentDto.getId());
		comment.setPostId(postId);

		// 4.  Hashtag Service 에 저장.
		// 5.  mention Service 에 멘션함.

		// 5.  Alarm Service 에 보냄. (Refactor)

		commentRepository.save(comment);
	}

	@Override
	public void deleteComment(Long commentId) {
		// 1. delete 하는 User 와 login 한 User 가 같아야 함. -> Error 처리.


		// 2. comment 없을 때 ERROR 처리 해줌. (ok)
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));


		// 3. comments Likes 다 없애야 함. (OK)
		commentRepository.delete(comment);
		commentLikesService.deleteAll(comment);

	}

	// 변경
	@Override
	public void updateComment(Long commentId, String description){

		// 1.  comment 없을 때 (ok)
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

		// 댓글 쓰려는 유저가 맞지 않을 때 -> from user server.  NOT IMPLEMENT

		comment.setDescription(description);
	}


	// 조회 (ok)
	@Override
	@Transactional(readOnly = true)
	public List<Comment> findAllComments(Long postId) {
		return commentRepository.findAllByPostId(postId);
	}


	// 좋아요 구현
	@Override
	public void addCommentLikes(Long commentId)
	{
		// Comment 가져옴. (에러 처리) (ok)
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));


		// 1. 현재 로그인 한 유저를 가져옴 (from UserService)
		// dummy data 로 대신함.
		User tempUser = new User();
		tempUser.setId(1L);
		tempUser.setUserName("Jongha");
		tempUser.setFullName("park");

		// 2. 에러 고치기 (refactoring)
		if (commentLikesRepository.findByUserAndComment(tempUser, comment).isPresent()){
			throw new CommentException(CommentExceptionType.NOT_FOUND_COMMENT);
		}

		// 3. DTO 로 고치기 (refactoring)
		comment.setLikes(comment.getLikes()+1);
		CommentLikes commentLikes = new CommentLikes();
		commentLikes.setComment(comment);
		commentLikes.setUser(tempUser);

		commentLikesRepository.save(commentLikes);

		// 알람 서비스 보내기. (refactoring)
	}

	@Override
	public void deleteCommentLikes(Long commentId){

		// Comment 좋아요 구현.
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
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));
		commentLikesRepository.delete(commentLikes);

		// 3. 알림 보내기 ( to Alarm service) -> Refactoring

	}

}
