package org.kosta.goodmove.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.goodmove.model.service.MemberService;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 회원 정보 관할 컨트롤러 : Controller
 * 
 * @author AreadyDoneTeam
 * @version 1
 */
@Controller
public class MemberController {
	@Resource
	private MemberService service;
	//회원정보수정시 비밀번호 암호화처리를 위한 객체를 주입받는다
	@Resource
	private BCryptPasswordEncoder passwordEncoder;
	/**
	 * 로그인에 관한 컨트롤러
	 * 
	 * @param memberVO
	 * @param request
	 * @return
	 */
/*	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String login(MemberVO memberVO, HttpServletRequest request) {
		MemberVO vo = service.login(memberVO);
		if (vo == null) {
			return "member/login_fail.tiles";
		} else {
			request.getSession().setAttribute("mvo", vo);
			return "redirect:home.do";
		}
	}*/
	@RequestMapping("login_fail.do")
    public String loginFail(){
    	return "member/login_fail.tiles";
    }
	/**
	 * 로그아웃에 관한 컨트롤러
	 * 
	 * @param request
	 * @return
	 */
/*	@RequestMapping("logout.do")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		return "redirect:home.do";
	}*/

	/**
	 * 회원가입에 관한 컨트롤러: tel을 데이터에 넘겨주기 위해 vo.setTel에 tel3개의 이름을 담아줌
	 * 
	 * @param vo
	 * @param tel1
	 * @param tel2
	 * @param tel3
	 * @return
	 */
	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	public String register(MemberVO vo, String tel1, String tel2, String tel3) {
		vo.setTel(tel1 + tel2 + tel3);
		service.register(vo);
		return "redirect:registerResultView.do?id=" + vo.getId();
	}

	/**
	 * 회원가입결과를 보여주는 컨트롤러
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("registerResultView.do")
	public ModelAndView registerResultView(String id) {
		MemberVO vo = service.findMemberById(id);
		return new ModelAndView("member/registers_result.tiles", "memberVO", vo);
	}

	/**
	 * 아이디 체크를 할때 보여주는 컨트롤러
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("idcheckAjax.do")
	@ResponseBody
	public String idcheckAjax(String id) {
		int count = service.idcheck(id);
		return (count == 0) ? "ok" : "fail";
	}

	/**
	 * 회원수정을 하기위한 컨트롤러 회원가입과 동일하게 Tel을 vo.setTel안에 합쳐줌
	 * 
	 * @param request
	 * @param memberVO
	 * @param tel1
	 * @param tel2
	 * @param tel3
	 * @return
	 */
	@RequestMapping(value = "updateMember.do", method = RequestMethod.POST)
	public String updateMember(HttpServletRequest request, MemberVO memberVO, String tel1, String tel2, String tel3) {
		// Spring Security 세션 회원정보를 반환받는다
		MemberVO pvo = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("Spring Security 세션 수정 전 회원정보:" + pvo);
		//변경할 비밀번호를 암호화한다 
		String encodePassword=passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encodePassword);
		memberVO.setTel(tel1 + tel2 + tel3);
		service.updateMember(memberVO);
		// 수정한 회원정보로 Spring Security 세션 회원정보를 업데이트한다
		pvo.setPassword(encodePassword);
		pvo.setAddr(memberVO.getAddr());
		pvo.setTel(memberVO.getTel());
		pvo.setAddr_detail(memberVO.getAddr_detail());
		pvo.setJob(memberVO.getJob());
		System.out.println("Spring Security 세션 수정 후 회원정보:" + pvo);
		return "member/update_result.tiles";
	}

	/**
	 * 비밀번호 체크를 하기위한 컨트롤러
	 * 
	 * @param password
	 * @return
	 */
	@RequestMapping("passwordCheck.do")
	public String passwordCheck(String password) {
		int trim = service.passwordCheck(password);
		return (trim == 0) ? "ok" : "fail";
	}

	/**
	 * 회원탈퇴를 위한 컨트롤러
	 * 
	 * @param request
	 * @param id
	 * @param password
	 * @return
	 */
	@RequestMapping("deleteMember.do")
	public String deleteMember(HttpServletRequest request, String id, String password) {
		MemberVO pvo = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		service.deleteMember(id, pvo.getPassword());
		return "redirect:logout.do";
	}

	/**
	 * 아이디찾기 기능 컨트롤러
	 * 
	 * @param request
	 * @param name
	 * @param tel1
	 * @param tel2
	 * @param tel3
	 * @return
	 */
	@RequestMapping("forgotId.do")
	public String forgotId(HttpServletRequest request, String name, String tel1, String tel2, String tel3) {
		System.out.println(name + tel1 + tel2 + tel3);
		MemberVO vo = service.forgotId(name, tel1 + tel2 + tel3);
		/* HttpSession session=request.getSession(); */
		if (vo == null) {
			return "member/login_fail.tiles";
		} else {
			request.setAttribute("id", vo.getId());
			return "member/forgotId_result.tiles";
		}
	}

	/**
	 * 비밀번호 찾기 기능 컨트롤러
	 * 
	 * @param request
	 * @param id
	 * @param name
	 * @param tel1
	 * @param tel2
	 * @param tel3
	 * @return
	 */
	@RequestMapping("forgotPass.do")
	public String forgotPass(HttpServletRequest request, String id, String name, String tel1, String tel2,
			String tel3) {
		MemberVO vo = service.forgotPass(id, name, tel1 + tel2 + tel3);
		if (vo == null) {
			return "member/login_fail.tiles";
		} else {
			request.setAttribute("id", id);
			request.setAttribute("pass", vo.getPassword());
			return "member/changePass.tiles";
		}
	}

	/**
	 * 비밀번호 찾기시 비밀번호 변경 컨트롤러
	 * 
	 * @param request
	 * @param id
	 * @param password
	 * @return
	 */
	@RequestMapping("changePass.do")
	public String chagePass(HttpServletRequest request, String id, String password) {
		System.out.println(id + password);
		String encodePassword=passwordEncoder.encode(password);
		service.changePass(id, encodePassword);
		return "member/changePass_result.tiles"; 	
	}
	/**
	 * 관리자 페이지에서 멤버 리스트 보기
	 * 
	 * @param model
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping("getMemberList_admin.do")
	public String getMemberList_admin(Model model, int pageNo ) {
		model.addAttribute("lvo", service.getMemberList_admin(pageNo));
		return "admin/memberList_admin.tiles2";
	}

	/**
	 * 관리자 멤버 삭제
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping("deleteMember_admin.do")
	public String deleteMember_admin(String id, Model model) {
		service.deleteMember_admin(id);
		int pageNo = 1;
		model.addAttribute("lvo", service.getMemberList_admin(pageNo));
		System.out.println("삭제완료!");
		return "admin/memberList_admin.tiles2";
	}
	/**
	 * 관리자 멤버 정보 수정 페이지 이동
	 * @param id
	 * @param model
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping("restoreMember_admin.do")
	public String restoreMember_admin(String id, Model model){
		service.restoreMember_admin(id);
		int pageNo = 1;
		model.addAttribute("lvo", service.getMemberList_admin(pageNo));
		System.out.println("삭제완료!");
		return "admin/memberList_admin.tiles2";
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping("updateMember_admin.do")
	public String updateMember_admin(String id, Model model) {
		model.addAttribute("mvo", service.findMemberById(id));
		return "admin/updateMember_admin.tiles2";
	}
	/**
	 * 관리자 멤버 정보 수정
	 * @param model
	 * @param memberVO
	 * @param tel1
	 * @param tel2
	 * @param tel3
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "updateMember_admin.do", method = RequestMethod.POST)
	public String updateMember_admin(Model model, MemberVO memberVO, String tel1, String tel2, String tel3) {
		memberVO.setTel(tel1 + tel2 + tel3);
		String encodePassword = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encodePassword);
		service.updateMember(memberVO);
		int pageNo = 1;
		model.addAttribute("lvo", service.getMemberList_admin(pageNo));
		return "admin/memberList_admin.tiles2";
	}
}
