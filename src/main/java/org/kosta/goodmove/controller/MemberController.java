package org.kosta.goodmove.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.goodmove.model.service.MemberService;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 회원 정보 관할 컨트롤러 : Controller
 * @author AreadyDoneTeam
 * @version 1
 */
@Controller
public class MemberController {
	@Resource
	private MemberService service;
	/**
	 * 로그인에 관한 컨트롤러
	 * @param memberVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	public String login(MemberVO memberVO,HttpServletRequest request){
		MemberVO vo=service.login(memberVO);
		if(vo==null){
			return "member/login_fail.tiles";
		}else{
			request.getSession().setAttribute("mvo", vo);
			return "home.tiles";
		}
	}
	/**
	 * 로그아웃에 관한 컨트롤러
	 * @param request
	 * @return
	 */
	@RequestMapping("logout.do")
	public String logout(HttpServletRequest request){
			HttpSession session=request.getSession(false);
			if(session!=null)
				session.invalidate();
			return "home.tiles";
	}
	/**
	 * 회원가입에 관한 컨트롤러: tel을 데이터에 넘겨주기 위해 
	 * vo.setTel에 tel3개의 이름을 담아줌
	 * @param vo
	 * @param tel1
	 * @param tel2
	 * @param tel3
	 * @return
	 */
	@RequestMapping(value="register.do", method=RequestMethod.POST)
public String register(MemberVO vo,String tel1,String tel2,String tel3){
		vo.setTel(tel1+tel2+tel3);
		service.register(vo);
		return "redirect:registerResultView.do?id=" +vo.getId();
	}
	/**
	 * 회원가입결과를 보여주는 컨트롤러
	 * @param id
	 * @return
	 */
	@RequestMapping("registerResultView.do")
	public ModelAndView registerResultView(String id){
		MemberVO vo=service.findMemberById(id);
		return new ModelAndView("member/registers_result.tiles","memberVO",vo);
	}
	/**
	 * 아이디 체크를 할때 보여주는 컨트롤러
	 * @param id
	 * @return
	 */
	@RequestMapping("idcheckAjax.do")
	@ResponseBody
	public String idcheckAjax(String id){
		int count=service.idcheck(id);
		return (count==0)? "ok":"fail";
	}
	/**
	 * 회원수정을 하기위한 컨트롤러
	 * 회원가입과 동일하게 Tel을 vo.setTel안에 합쳐줌
	 * @param request
	 * @param memberVO
	 * @param tel1
	 * @param tel2
	 * @param tel3
	 * @return
	 */
	@RequestMapping(value="updateMember.do",method=RequestMethod.POST)
	public String updateMember(HttpServletRequest request,MemberVO memberVO, String tel1, String tel2, String tel3){	
			
			HttpSession session=request.getSession(false);
		if(session!=null&&session.getAttribute("mvo")!=null){	
				session.setAttribute("mvo", memberVO);
				memberVO.setTel(tel1+tel2+tel3);
				service.updateMember(memberVO);
				return "member/update_result.tiles";
		}else{
			return "home.tiles";
			}	
	}
	/**
	 * 비밀번호 체크를 하기위한 컨트롤러
	 * @param password
	 * @return
	 */
	@RequestMapping("passwordCheck.do")
	public String passwordCheck(String password){
		int trim=service.passwordCheck(password);
		return (trim==0)? "ok":"fail";
	}
	/**
	 * 회원탈퇴를 위한 컨트롤러
	 * @param request
	 * @param id
	 * @param password
	 * @return
	 */
	@RequestMapping("deleteMember.do")
	public String deleteMember(HttpServletRequest request,String id,String password){
		System.out.println(id + password);
		HttpSession session=request.getSession(false);
		service.deleteMember(id, password);
		if(session!=null){
			session.invalidate();
		}
		return "member/delete_result.tiles";
		
}
	@RequestMapping("forgotId.do")
	public String forgotId(HttpServletRequest request,String name,String tel1,String tel2,String tel3){
		System.out.println(name+tel1+tel2+tel3);
		MemberVO vo=service.forgotId(name, tel1+tel2+tel3);
		/*HttpSession session=request.getSession();*/
		if(vo==null){
			return "member/login_fail.tiles";
		}else{	
			request.setAttribute("id", vo.getId());
			return "member/forgotId_result.tiles";
		}
	}
		@RequestMapping("forgotPass.do")
		public String forgotPass(HttpServletRequest request, String id,String name,String tel1, String tel2, String tel3){
			System.out.println(id+name+tel1+tel2+tel3);
			MemberVO vo=service.forgotPass(id, name, tel1+tel2+tel3);
		if(vo==null){
			return "member/login_fail.tiles";
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("pass", vo.getPassword());
			return "member/changePass.tiles";
		}
	}
	@RequestMapping("changePass.do")
	public String chagePass(String id,String password){
		System.out.println(id+password);
		service.changePass(id, password);
		return "member/changePass_result.tiles";
	}
}






















