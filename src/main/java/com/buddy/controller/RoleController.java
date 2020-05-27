package com.buddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.entity.Role;
import com.buddy.entity.vo.Result;
import com.buddy.entity.vo.ResultCode;
import com.buddy.service.RoleService;

@RestController
@RequestMapping("/buddyce")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	@GetMapping(value = "/{id}")
	public Result getById(@PathVariable("id") int id) {
		Role role = roleService.findById(id);
		return new Result(ResultCode.SUCCESS,role);
		
	}
	
}
