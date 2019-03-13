package com.nastech.upmureport.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public void UserRegister(User User) {
		userRepository.save(User);
	}
	public User SearchUserById(Integer id) {
		User user = userRepository.findOneByUserId(id);
		return user;
	}
	public User SearchUserByName(String userName) {
		User user = userRepository.findOneByUserName(userName);
		return user;
	}	
	public List<User> SearchUsers(){
		List<User> users;
		users=userRepository.findAll();
		return users;
	}
	public boolean UserLogin(Integer id,String pass) {
		User user = userRepository.findOneByUserId(id);
		if(pass.equals(user.getUserPass())) {
			return true;
		}
		else
			return false;
	}
	
	

}
