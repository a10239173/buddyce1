package com.buddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buddy.dao.UserDao;
import com.buddy.entity.User;
import com.buddy.utils.MD5Util;

import cn.hutool.core.util.RandomUtil;

@Service
public class UserService {
	
	
	@Autowired
	UserDao userDao;
	
	public User findByUsernameAndPassword(String tel,String password) throws Exception{
		
		List<User> users; 
		
		users =userDao.findByTelAndPassword(tel, password);
			
		if(users!=null) {
			return users.get(0);
		}else {
			return null;
		}
		
	}
	
	
	public void save(String tel,String password,String pinviteCode) {
		User user = new User();
		user.setTel(tel);
		/*
		 * 自身邀请码
		 */
		String inviteCode = RandomUtil.randomString(6);
		
		String md5 = MD5Util.getMD5(password);
		user.setPassword(md5);
		user.setMyinviteCode(inviteCode);
		/*
		 * 如果被邀请码不为空
		 */
		if(null!=pinviteCode) {
			user.setPinviteCode(pinviteCode);
		}
		
		userDao.save(user);
	}
	
	public User findByTel(String tel) throws Exception {
		
		List<User> users = userDao.findByTel(tel);
		if(users!=null) {
			return users.get(0);
		}else {
			return null;
		}
	}
	
	public User findById(int authorid) {
		
		return userDao.findOne(authorid);
		
	}
	
	public List<User> findAll(){
		return userDao.findAll();
	}
	
	public List<User> findByPinviteCode(String code){
		return userDao.findByPinviteCode(code);
	}

	public User findByMyinviteCode(String code){
		User user = userDao.findByMyinviteCode(code).get(0);
		return user;
	}

}
