package com.nastech.upmureport.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.UserDto;
import com.nastech.upmureport.service.UserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
	@Autowired
	UserService userService;
    /*
    @GetMapping(value = "/login")
    public String login(User user){
    	//userService.userLogin(user.getUserId(), user.getUserPass());
    	System.out.println(user.getUserId());
    	return "_template";
    }*/
    @PostMapping(value = "/login")
    public UserDto login(@RequestBody UserDto user, HttpServletRequest request){
    	UserDto loginedUserDto = userService.userLogin(user);
    	System.out.println(loginedUserDto);
    	if(loginedUserDto != null) {
    		HttpSession session = request.getSession();
    		session.setAttribute("userDto", (Object)loginedUserDto);
    		System.out.println("send loginedUser to View:"+loginedUserDto);
    		return loginedUserDto;
    	}
    	else
    	{
    		return null;
    	}
    }
    @CrossOrigin
    @PostMapping(value = "/userlist")
    public List<UserDto> userList(@RequestBody UserDto user, HttpServletRequest request){
    	System.out.println("주니어목록줘!");
    	List<UserDto> juniorList = userService.findMyJuniors(user);
    	if( !juniorList.isEmpty()) {
    		System.out.println("내 부하들 리스트:"+juniorList);
    		return juniorList;
    	}
    	else
    	{
    		return null;
    	}
    }
    
    @CrossOrigin
    @GetMapping(value = "/regi")
    public String register() {
    	UserDto user= new UserDto("190313", "Test", "1q2w3e4r", "연구소", "사원", false);
    	userService.userRegister(user);
    	return "redirect:/";
    }
}