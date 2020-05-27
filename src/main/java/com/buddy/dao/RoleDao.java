package com.buddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.buddy.entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer>,JpaSpecificationExecutor<Role>{

}
