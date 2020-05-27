package com.buddy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 对autho_invite进行操作
 */

@Entity
@Table(name = "autho_invite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invite {
	
	@Id
	private int id;
	
	private int authorId;
	
	private int pid;
	
	/*
	 * 层级：1表示1级，2表示2级
	 */
	private int level;

}
