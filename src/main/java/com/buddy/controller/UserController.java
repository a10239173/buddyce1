package com.buddy.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.exceptions.ClientException;
import com.buddy.entity.SMSModel;
import com.buddy.entity.User;
import com.buddy.entity.vo.Result;
import com.buddy.entity.vo.ResultCode;
import com.buddy.service.InviteService;
import com.buddy.service.UserService;
import com.buddy.utils.SendMessageUtils;
import com.buddy.utils.ValidateCodeUtils;

import cn.hutool.core.util.RandomUtil;

@RestController
@RequestMapping(value = "/buddyce")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	InviteService inviteService;
	
	
	/*
	 * 用户登陆
	 */
	@GetMapping(value = "/user")
	public Result findByUsernameAndPassword(@RequestParam(value = "tel") String tel,@RequestParam(value = "password") String password,HttpSession session) {
		
		User user;
		try {
			user = userService.findByUsernameAndPassword(tel, password);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Result(ResultCode.FAIL);
		}
		if(null!=user) {
			session.setAttribute("user", user);
			return new Result(ResultCode.SUCCESS);
			
		}else {
			return new Result(ResultCode.FAIL);
		}
		
		
	}
	
	/*
	 * 用户注册
	 */
	@RequestMapping(value = "/login")
	public Result register(@RequestParam(value ="code" )String code
			,@RequestParam(value = "tel") @Valid String tel
			,@RequestParam(value = "password") @Valid String password
			,HttpServletRequest request
			,@RequestParam(value = "pinviteCode",required=false)String pinviteCode
			,@RequestParam(value ="phoneCode" )String phoneCode
			) throws Exception {
		
		User user;
		
		request.setCharacterEncoding("utf-8");
		/*
		 * 判断图形验证码是否正确
		 */
		if(!code.equals(request.getSession().getAttribute("code"))) {
			return new Result(ResultCode.ValidateCode_EXIST);	
		}
		
		/*
		 * 手机长度检验
		 */
		if(tel.length()!=11) {
			return new Result(ResultCode.FAIL);
		}
		
		/*
		 * 密码校验
		 */
		if(password.matches("[A-Z][a-z][0-9]")==false) {
			return new Result(ResultCode.FAIL);
		}
		
		
		/*
		 * 验证手机验证码是否正确
		 * 
		 */
		if(!phoneCode.equals(request.getSession().getAttribute("phoneCode"))) {
			return new Result(ResultCode.phoneCode_Error);	
		}
		
		/*
		 * 验证手机是否存在
		 * 
		 */
		try {
		user = userService.findByTel(tel);
		}catch(Exception e){
			return new Result(ResultCode.FAIL);
		}
		if(null==user) {
			User temp = new User();
			temp.setTel(tel);
			temp.setPassword(password);
			userService.save(tel, password, pinviteCode);
			
			/*
			 * 用户注册成功后要查询该用户的id，并在author_invite表中生成一二级好友对应关系
			 */
			User userRegistered = userService.findByTel(tel);
			int id = userRegistered.getId();
			
			/*
			 * 在author_invite表生成对应的一二级关系
			 */
			inviteService.level(id);
			
			return new Result(ResultCode.SUCCESS);
		}
		
		return new Result(false,10005,"该用户手机号已存在");
	}
	
	
	/*
	 * 生成验证码
	 * 
	 */
	@RequestMapping(value = "/getValidateCode")
	  public String getValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    // 设置响应的类型格式为图片格式
	    response.setContentType("image/jpeg");
	    // 禁止图像缓存。
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	 
	    HttpSession session = request.getSession();
	 
	    ValidateCodeUtils vCode = new ValidateCodeUtils (120, 40, 5, 100);
	    session.setAttribute("code", vCode.getCode());
	    vCode.write(response.getOutputStream());
	    return null;
	  }
	
	
	/*
	 * 发送手机短信
	 */
	@RequestMapping(value = "/sendMessage")
	public Result sendMsg(@RequestParam(value = "tel")String tel,HttpServletRequest request) {
		
		int phoneCode = RandomUtil.randomInt(1000, 9999);
		SMSModel smsModel = new SMSModel();
		smsModel.setTel(tel);
		smsModel.setCode(phoneCode);
		request.getSession().setAttribute("phoneCode", smsModel.getCode());
		
		
		try {
			SendMessageUtils.sendMssage(smsModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Result(ResultCode.FAIL);
		}
		return new Result(ResultCode.SUCCESS);
		
	}
	
	
	
	
	

}
