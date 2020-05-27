package com.buddy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*
 * 用于存放短信信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SMSModel {
	
	private int code;
	private String tel;

}
