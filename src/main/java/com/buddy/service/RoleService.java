package com.buddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buddy.dao.RoleDao;
import com.buddy.entity.Role;

@Service
public class RoleService {
	
	@Autowired
	RoleDao roleDao;
	
	public Role findById(int id) {
		
		return roleDao.findOne(id);
	}

}
