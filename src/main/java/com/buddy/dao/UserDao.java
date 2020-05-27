package com.buddy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.buddy.entity.User;

public interface UserDao extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User>{

	 List<User> findByTelAndPassword(String tel,String password);
	 
	 List<User> findByTel(String tel);
	 
	 List<User> findByPinviteCode(String pinviteCode);
	 
	 List<User> findByMyinviteCode(String myinviteCode);
}
