package com.example.comment.exception;

import org.springframework.http.HttpStatus;

public enum CommentExceptionType implements BaseExceptionType
{
	NOT_FOUND_COMMENT(602, HttpStatus.OK, "커맨드 정보가 없습니다.");

	//에러 처리 해야 함!
	// NOT IMPLEMENT
	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;

	 CommentExceptionType(int errorCode , HttpStatus httpStatus, String errorMessage){
		this.errorCode =errorCode;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

	@Override
	public int getErrorCode() {
		return this.errorCode;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
