package com.buddy.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8111553032107551376L;

	@Id
	private int id;
	
	private String roleName;
	
	private String name;
	

}
