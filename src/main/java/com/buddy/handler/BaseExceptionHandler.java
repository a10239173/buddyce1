package com.buddy.handler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buddy.entity.vo.Result;
import com.buddy.entity.vo.ResultCode;
import com.buddy.exception.CommonException;



@ControllerAdvice
public class BaseExceptionHandler {
	
	
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Result error(HttpServletRequest request,HttpServletResponse response,Exception e) {
		e.printStackTrace();
		if(e.getClass()==CommonException.class) {
			CommonException ce =	(CommonException)e;
			Result result = new Result(ce.getResultCode());
			return result;
			
		}else {
			Result result = new Result(ResultCode.SERVER_ERROR);
			return result;
		}
		
	}

}
