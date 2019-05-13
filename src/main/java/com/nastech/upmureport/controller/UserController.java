package com.nastech.upmureport.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.MemAuthDto;
import com.nastech.upmureport.domain.dto.MemberDto;
import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.domain.entity.Career;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberSystem;
import com.nastech.upmureport.domain.entity.Role;
import com.nastech.upmureport.domain.entity.UserRole;
import com.nastech.upmureport.domain.repository.AuthInfoRepository;
import com.nastech.upmureport.domain.repository.CareerRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;
import com.nastech.upmureport.domain.repository.UserRoleRepository;
import com.nastech.upmureport.domain.security.AuthenticationToken;
import com.nastech.upmureport.domain.security.CustomUserDetails;
import com.nastech.upmureport.domain.security.UserService;
import com.nastech.upmureport.service.AuthInfoService;
import com.nastech.upmureport.service.MemberService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memRepo;
	@Autowired
	MemberSystemRepository memSys;
	@Autowired
	AuthInfoService authInfoService;
	@Autowired
	AuthInfoRepository authInfoRepository;
	
	@Autowired AuthenticationManager authenticationManager;
	@Autowired UserService userService;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	CareerRepository careerRepository;
	
    /*
    @GetMapping(value = "/login")
    public String login(User user){
    	//memberService.userLogin(user.getUserId(), user.getUserPass());
    	System.out.println(user.getUserId());
    	return "_template";
    }*/
//    @PostMapping(value = "/login")  //기존 로그인 서비스
//    public MemberDto login(@RequestBody AuthInfo user, HttpServletRequest request){
//    	MemberDto loginedUserDto = authInfoService.userLogin(user);
//    	if(loginedUserDto != null) {
//    		HttpSession session = request.getSession();
//    		session.setAttribute("userDto", (Object)loginedUserDto);
//    		return loginedUserDto;
//    	}
//    	else
//    	{
//    		return null;
//    	}
//    }
	
	@PostMapping(value = "/login")
    public Map<?, ?> login(@RequestBody AuthInfo user, HttpServletRequest request){
		String username = user.getUsername();
		String password = user.getPassword();
		HttpSession session = request.getSession();
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);  //인증된것 contextHolder에 넣어줌
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY , SecurityContextHolder.getContext());
		
		AuthInfo authUser = authInfoRepository.findOneByUsername(username);
		List<UserRole> userRoles = userRoleRepository.findAllByUsername(username);
		if(authUser==null || userRoles.isEmpty() ) return null;
		CustomUserDetails customUser = new CustomUserDetails(authUser, userRoles);
		System.out.println("customUser"+customUser);
		System.out.println("-----customUser"+customUser.getAuthorities());
		
		Map<String, Object> returnObj = new HashMap<String, Object>();
		
		returnObj.put("authToken", new AuthenticationToken(customUser.getUsername(), customUser.getAuthorities(), session.getId()));
		returnObj.put("memberInfo", authUser.getMember());
		
		return returnObj;
    }
	@PostMapping(value ="/modify")
	public void authModifyAPI(@RequestBody MemAuthDto memAuthDto) {
//		System.out.println(mem); 
		Member m=memAuthDto.getMem();
		AuthInfo modifyAuthInfo = memAuthDto.getAuth();
		AuthInfo basicAuthInfo = authInfoRepository.findOneByMember(m);
		System.out.println(basicAuthInfo);
		System.out.println(m);
		
		System.out.println("들어온 것"+modifyAuthInfo);
		
//		//아이디가 변경되었다면 아이디 변경 보류
//		if(modifyAuthInfo.getUsername()!=null) {
//			basicAuthInfo.setUsername(modifyAuthInfo.getUsername());
//		}
		System.out.println("변경 될 계정"+basicAuthInfo);
		authInfoRepository.save(basicAuthInfo);
		
	}
	
    @CrossOrigin
    @PostMapping(value = "/userlist")
    public List<MemberDto> userList(@RequestBody MemberDto user, HttpServletRequest request){
    	List<MemberDto> juniorList = memberService.findMyJuniors(user);
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
    

    @PostMapping(value= "/register")
    public MemberDto userRegister(@RequestBody MemAuthDto memAuth) {
    	Member mem=memAuth.getMem();
    	AuthInfo auth = memAuth.getAuth();
    	
    	mem.setJoinDate(LocalDate.now());
    	MemberDto savedMem= memberService.userRegister(mem.toDto());  //MemberInfo 등록
    	if(savedMem == null) return null;
    	auth.setRole(Role.ROLE_USER);
    	auth.setMember(savedMem.toEntity());
    	UserRole ur1 = UserRole.builder().role(Role.ROLE_USER).username(auth.getUsername()).build();
    	authInfoRepository.save(auth);
    	userRoleRepository.save(ur1);
    	Member admin = memRepo.findOneByName("관리자");
    	MemberSystem ms1 = MemberSystem.builder().senior(admin).junior(savedMem.toEntity()).build();
    	memSys.save(ms1);
    	Career c1 = Career.builder().active(true).dept("미등록").posi("미등록").member(savedMem.toEntity())
    			.startDate(LocalDate.now())
    			.build();
    	careerRepository.save(c1);
    	System.out.println(savedMem+"auth"+auth);
    	return savedMem;
  	}
    
    @PostMapping(value="/idcheck")
    public AuthInfo idCheck(@RequestBody AuthInfo auth) {
    	System.out.println(auth);
    	String name=auth.getUsername();
    	AuthInfo temp= authInfoRepository.findOneByUsername(name);
    	System.out.println(temp);
    	if (temp != null) {  // 이미 있는 아이디이므로 null반환
    		return null;
    	}
    	else
    		return auth;
    }
    
    @GetMapping(value="/getRetire")
    public void getRetire() {
    	
    }
    
    
    

    @GetMapping(value = "/listAll")
    public ResponseEntity<List<MemberDto>> listAll() {
    	return ResponseEntity.ok().body(memberService.listAll());
    }

//    
//    @CrossOrigin
//    @GetMapping(value = "/regi")
//    public String register() {
//    	UserDto user= new UserDto("190313", "Test", "1q2w3e4r", "연구소", "사원", false);
//    	memberService.userRegister(user);
//    	return "redirect:/";
//    }
}