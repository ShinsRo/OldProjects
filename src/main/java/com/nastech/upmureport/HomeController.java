package com.nastech.upmureport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nastech.upmureport.domain.dto.UserDto;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/")
    public String index(HttpServletRequest req, HttpServletResponse res) {
        return "index";
    }

    @GetMapping(value = "/temp")
    public String temp() {
        return "_template";
    }
    /*
    @GetMapping(value = "/login")
    public String login(User user){
    	//userService.userLogin(user.getUserId(), user.getUserPass());
    	System.out.println(user.getUserId());
    	return "_template";
    }*/
    @RequestMapping(value = "/login",method= {RequestMethod.POST , RequestMethod.GET} )
    public String login(UserDto user, HttpServletRequest request){
    	UserDto loginedUserDto = userService.userLogin(user);
    	if(loginedUserDto != null) {
    		HttpSession session = request.getSession();
    		session.setAttribute("userDto", (Object)loginedUserDto);
    		return "index";
    	}
    	else
    	{
    		return "redirect:/";
    	}
    }
    @GetMapping(value = "/regi")
    public String register() {
    	UserDto user= new UserDto("190313", "Test", "1q2w3e4r", "연구소", "사원", false);
    	userService.userRegister(user);
    	return "redirect:/";
    }
    
}
