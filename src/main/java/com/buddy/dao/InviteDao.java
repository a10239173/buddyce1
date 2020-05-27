package com.buddy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.buddy.entity.Invite;

public interface InviteDao extends JpaRepository<Invite, Integer>,JpaSpecificationExecutor<Invite>{

	
	public 	List<Invite> findByPid(int pid);
}
