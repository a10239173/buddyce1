package com.buddy.entity.vo;

public enum ResultCode {

	
	SUCCESS(true,10000,"操作成功"),
	FAIL(false,10001,"操作失败"),
	UNAUTHENTICATED(false,10003,"你还未登陆"),
	UNAUTHORISE(false,10004,"权限不足"),
	REGISTER_FAIL(false,10005,"该用户已存在"),
	ValidateCode_EXIST(false,10006,"图形码输入内容有误，请重新输入"),
	phoneCode_Error(false,10007,"短信验证码错误"),
	SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试");
	
	boolean success;
	int code;
	String message;
	
	
	private ResultCode(boolean success,int code,String message) {
		
		this.success = success;
		this.code = code;
		this.message = message;
	}
	
	public boolean success() {
		return success;
	}
	
	public int code() {
		return code;
	}
	
	public String message() {
		return message;
	}
	
}
