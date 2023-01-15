package com.example.comment.exception;

import org.springframework.http.HttpStatus;

public enum CommentExceptionType implements BaseExceptionType {
	NOT_FOUND_COMMENT(602, HttpStatus.OK, "커맨드 정보가 없습니다."),
	VALUE_MAX_EXCEPTION(602, HttpStatus.OK, "정수 범위를 넘습니다."),
	VALUE_MIN_EXCEPTION(602, HttpStatus.OK, "정수 범위가 음수입니다.");

	//에러 처리 해야 함!
	// NOT IMPLEMENT
	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;

	CommentExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
		this.errorCode = errorCode;
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
