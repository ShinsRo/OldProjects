package com.nastech.upmureport.domain.dto;

import com.nastech.upmureport.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String userId;
	private String userName;
	private String userPass;
	private String dept;
	private String posi;
	private Boolean deleteFlag;
	
	public User toEntity() {
		return User.builder().
				userId(userId).
				userName(userName).
				userPass(userPass).dept(dept).posi(posi).build();
		}

}