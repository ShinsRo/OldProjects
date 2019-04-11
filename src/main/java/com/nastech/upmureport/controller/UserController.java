package com.nastech.upmureport.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.MemberDto;
import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.service.AuthInfoService;
import com.nastech.upmureport.service.MemberService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
	@Autowired
	MemberService userService;
	@Autowired
	AuthInfoService authInfoService;
    /*
    @GetMapping(value = "/login")
    public String login(User user){
    	//userService.userLogin(user.getUserId(), user.getUserPass());
    	System.out.println(user.getUserId());
    	return "_template";
    }*/
    @PostMapping(value = "/login")
    public MemberDto login(@RequestBody AuthInfo user, HttpServletRequest request){
    	MemberDto loginedUserDto = authInfoService.userLogin(user);
    	if(loginedUserDto != null) {
    		HttpSession session = request.getSession();
    		session.setAttribute("userDto", (Object)loginedUserDto);
    		return loginedUserDto;
    	}
    	else
    	{
    		return null;
    	}
    }
	@PostMapping(value = "/test")
	public void a() {
		System.out.println("test");
	}
	
    @CrossOrigin
    @PostMapping(value = "/userlist")
    public List<MemberDto> userList(@RequestBody MemberDto user, HttpServletRequest request){
    	List<MemberDto> juniorList = userService.findMyJuniors(user);
    	if( !juniorList.isEmpty()) {
    		System.out.println("비어있지안흥ㅁ");
    		return juniorList;
    	}
    	else
    	{
    		System.out.println("비어있음");
    		return null;
    	}
    }
//    
//    @CrossOrigin
//    @GetMapping(value = "/regi")
//    public String register() {
//    	UserDto user= new UserDto("190313", "Test", "1q2w3e4r", "연구소", "사원", false);
//    	userService.userRegister(user);
//    	return "redirect:/";
//    }
}