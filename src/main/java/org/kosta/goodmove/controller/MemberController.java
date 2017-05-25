package org.kosta.goodmove.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.goodmove.model.service.MemberService;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.stereotype.Controller;
/**
 * 회원 정보 관할 컨트롤러 : Controller
 * @author AreadyDoneTeam
 * @version 1
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class MemberController {
	@Resource
	private MemberService service;

	@RequestMapping(value="login.do",method=RequestMethod.POST)
	public String login(MemberVO memberVO,HttpServletRequest request){
		MemberVO vo=service.login(memberVO);
		if(vo==null){
			return "member/logins.tiles";
		}else{
			request.getSession().setAttribute("mvo", vo);
			return "home.tiles";
		}
	}
	@RequestMapping("logout.do")
	public String logout(HttpServletRequest request){
			HttpSession session=request.getSession(false);
			if(session!=null)
				session.invalidate();
			return "home.tiles";
	}
	@RequestMapping(value="register.do", method=RequestMethod.POST)
public String register(MemberVO vo){
		service.register(vo);
		return "redirect:registerResultView.do?id=" +vo.getId();
	}
	@RequestMapping("registerResultView.do")
	public ModelAndView registerResultView(String id){
		MemberVO vo=service.findMemberById(id);
		return new ModelAndView("member/registers_result.tiles","memberVO",vo);
	}
	@RequestMapping("idcheckAjax.do")
	@ResponseBody
	public String idcheckAjax(String id){
		int count=service.idcheck(id);
		return (count==0)? "ok":"fail";
	}
}
