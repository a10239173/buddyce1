package com.buddy.exception;

import com.buddy.entity.vo.ResultCode;

import lombok.Getter;

@Getter
public class CommonException extends Exception{

	private ResultCode resultCode;
	
	public CommonException(ResultCode resultCode) {
		this.resultCode = resultCode;
	}
	
}
