package com.example.comment.exception;

public abstract class BaseException extends RuntimeException{
	public abstract BaseExceptionType getExceptionType();
}