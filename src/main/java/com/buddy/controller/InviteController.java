package com.buddy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.entity.User;
import com.buddy.entity.vo.Result;
import com.buddy.entity.vo.ResultCode;
import com.buddy.service.InviteService;
import com.buddy.service.UserService;

import cn.hutool.core.util.RandomUtil;

/*
 * 邀请系统
 */
@RestController
@RequestMapping("/buddyce")
public class InviteController {
	
	@Autowired
	InviteService InviteService;

	/*
	 * 邀请机制
	 */
	@RequestMapping(value = "/invite")
	public Result invite(HttpSession session) {
		
		
		User user = (User)session.getAttribute("user");
		if(user==null) {
			return new Result(ResultCode.UNAUTHENTICATED);
		}
		/*
		 * 用户自身的邀请码，用于邀请其他人
		 */
		String myinviteCode = user.getMyinviteCode();
		
		List<String> data = InviteService.invite();
		data.add(myinviteCode);
		
		return new Result(ResultCode.SUCCESS,data);
		
	}
	
	
	
	

	
	
	
}
