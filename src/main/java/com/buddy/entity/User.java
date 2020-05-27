package com.buddy.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable{
	
	
	private static final long serialVersionUID = -339051044845823686L;

	@Id
	private int id;
	
	
	private String userName;
	
	@NotNull(message = "密码不能为空")
	@Pattern(regexp = "[A-Z][a-z][0-9]")
	@Length(min = 8)
	private String password;
	
	private String name;
	
	@NotNull(message = "手机号不能为空")
	@Length(max = 11,min = 11,message = "请输入正确的手机号")
	private String tel;
	
	private String email;
	
	private String role;
	
	private int articleNum;
	
	private String picture;
	
	private String context;
	
	
	/*
	 * 自身邀请码
	 */
	private String myinviteCode;
	
	/*
	 * 被邀请码
	 */
	private String pinviteCode;

}
