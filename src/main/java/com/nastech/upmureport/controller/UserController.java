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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.MemAuthCareerDto;
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
import com.nastech.upmureport.service.CareerService;
import com.nastech.upmureport.service.MemberService;

@RestController
@RequestMapping(value = "/api/users")
/**
 * 
 * @author 마규석
 * 유저(사원)과 관련 된 기능들을 위한 Controller
 * 
 */

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
	@Autowired
	CareerService careerService;
	
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
	
	/**
	 * @author 마규석
	 * @param user
	 * @param request
	 * @return 로그인에 성공 한 유저의 token 과 유저정보
	 */
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
	public AuthInfo authModifyAPI(@RequestBody MemAuthCareerDto memAuthDto) {
//		System.out.println(mem); 
		Member m=memAuthDto.getMem();
		AuthInfo modifyAuthInfo = memAuthDto.getAuth();
		AuthInfo basicAuthInfo = authInfoRepository.findOneByMember(m);
		if(basicAuthInfo==null) return null;
		System.out.println(basicAuthInfo);
		System.out.println(m);
		System.out.println("들어온 것"+modifyAuthInfo);
		//비밀번호가 변경되었다면 비밀번호 변경!
		if(modifyAuthInfo.getPassword() != null){
			basicAuthInfo.setPassword(modifyAuthInfo.getPassword());
		}
		
//		//아이디가 변경되었다면 아이디 변경 보류
//		if(modifyAuthInfo.getUsername()!=null) {
//			basicAuthInfo.setUsername(modifyAuthInfo.getUsername());
//		}
		System.out.println("변경 될 계정"+basicAuthInfo);
		authInfoRepository.save(basicAuthInfo);
		return basicAuthInfo;
	}
	
	@PutMapping(value="/modify/phone")
	public Member phoneModifyAPI(@RequestBody Member mem) {
		System.out.println("폰컨"+mem);
		Member modifyMem=memberService.modifyPhone(mem);
		return modifyMem;
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
    

    /**
     * 새로운 사원 등록시 멤버 등록 auth 등록 userRole등록 career 등록 
     * @author 마규석
     * @param memAuthCareer 새로운 사원을 등록 위해 Member,Auth,Career가 모두 필요함
     * @return 등록 된 사원의 정보
     */
    
    @PostMapping(value= "/register")
    public MemberDto userRegister(@RequestBody MemAuthCareerDto memAuthCareer) {
    	System.out.println("등록");
    	Member mem=memAuthCareer.getMem();			//멤버정보를 꺼냄
    	AuthInfo auth = memAuthCareer.getAuth();	//Auth 정보를 꺼냄 (아이디,비밀번호)
    	Career career = memAuthCareer.getNewCar();	//커리어 정보를 꺼냄
    	mem.setJoinDate(LocalDate.now());			//새로 등록 된 날짜를 지정
    	
    	/*
    	 * 멤버 정보 등록
    	 */
    	MemberDto savedMem= memberService.userRegister(mem.toDto());  //MemberInfo 등록
    	if(savedMem == null) return null;								//MemberInfo 등록에 실패시 사원 등록 실패 
    	//AuthInfo 등록
    	AuthInfo savedAuth=authInfoService.registerAuth(auth, savedMem.toEntity());
    	
    	/*UserRole 등록 SpringSecurity를 사용하기 위함 */
    	UserRole ur1 = UserRole.builder().role(Role.ROLE_USER).username(savedAuth.getUsername()).build();
    	userRoleRepository.save(ur1);
    	
    	
    	Member admin = memRepo.findOneByName("관리자");
    	MemberSystem ms1 = MemberSystem.builder().senior(admin).junior(savedMem.toEntity()).build();
    	memSys.save(ms1);
    	
    	/* 새로운 커리어 등록 */
    	Career c1 = careerService.careerRegister(savedMem.toEntity(), career);
    	
    	System.out.println("확인11"+savedMem+"auth"+savedAuth+"career"+c1);
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
    @PostMapping(value="/eidcheck")
    public Member eidCheck(@RequestBody Member mem) {
    	System.out.println(mem);
    	String eid=mem.getEid();
    	Member temp = memRepo.findOneByEid(eid);
    	System.out.println(temp);
    	if (temp != null) {  // 이미 있는 사번이므로 null반환
    		return null;
    	}
    	else
    		return mem;
    }
    
    @PostMapping(value="/retire")
    public MemberDto retire(@RequestBody MemberDto memDto) {
    	System.out.println(memDto);
    	MemberDto retireMem = memberService.retireMember(memDto);
    	if(retireMem==null) return null;
    	else return retireMem;
    }
    
    
    
    ////현재 사원들만 (퇴사x)
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