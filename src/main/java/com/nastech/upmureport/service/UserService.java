package com.nastech.upmureport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.UserDto;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public UserDto userRegister(UserDto user) {
		User newUser = user.toEntity();
		if ( userRepository.findOneByUserId(newUser.getUserId()) == null) {
			userRepository.save(newUser);
			return user;
		}
		else
			return null;
	}
	public UserDto searchUserById(UserDto userDto) {
		String id=userDto.getUserId();
		User user = userRepository.findOneByUserId(id);
		if(user != null)
			return userDto;
		else return null;
	}
	public UserDto searchUserByName(UserDto userDto) {
		String userName=userDto.getUserName();
		User user = userRepository.findAllByUserName(userName).get(0);
		if(user != null) 
			return userDto;
		else return null;
	}	
	public List<User> searchUsers(){
		List<User> users;
		users=userRepository.findAll();
		return users;
	}
	public UserDto userLogin(UserDto user) {
		String id = user.getUserId();
		String pass = user.getUserPass();
		System.out.println(id+pass);
		User loginedUser = userRepository.findOneByUserId(id);
		if(loginedUser == null) return null;
		if(pass.equals(loginedUser.getUserPass())) {
			return user;
		}
		else
			return null;
	}
	public String searchPassword(UserDto userDto) {
		String id=userDto.getUserId();
		User user = userRepository.findOneByUserId(id);
		return user.getUserPass();
	}
	public void deleteUser(User user) {
		userRepository.delete(user);
	}
	public void userModifyById(String id,UserDto userDto) {
		User user = userDto.toEntity();
		User originUser = userRepository.findOneByUserId(id);
		originUser.setDept(user.getDept());
		originUser.setPosi(user.getPosi());
		originUser.setUserPass(user.getUserPass());
		userRepository.save(originUser);
	}
	
	

}
