package com.example.comment.exception;

public class CommentException extends BaseException {
	private BaseExceptionType baseExceptionType;

	public CommentException(BaseExceptionType baseExceptionType) {
		this.baseExceptionType = baseExceptionType;
	}

	@Override
	public BaseExceptionType getExceptionType() {
		return baseExceptionType;
	}
}
