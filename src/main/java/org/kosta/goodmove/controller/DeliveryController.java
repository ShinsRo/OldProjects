package org.kosta.goodmove.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.goodmove.model.service.DeliveryService;
import org.kosta.goodmove.model.vo.DeliveryVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeliveryController {

	@Resource
	private DeliveryService service;
	
	@RequestMapping(value="login_delivery.do",method=RequestMethod.POST)
	public String login(DeliveryVO dvo,HttpServletRequest request){
		DeliveryVO vo=service.login(dvo);
		if(vo==null){
			return "member/login_fail.tiles";
		}else{
			request.getSession().setAttribute("mvo", vo);
			return "redirect:home.do";
		}
	}
	/**
	 * 로그아웃에 관한 컨트롤러
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "register_delivery.do", method = RequestMethod.POST)
	public String register(DeliveryVO dvo, String tel1, String tel2, String tel3) {
		dvo.setTel(tel1 + tel2 + tel3);
//		service.register(dvo);
		System.out.println(dvo+" 회원가입 완료");
		return "redirect:home.do";
	}

	@RequestMapping("logout_delivery.do")
	public String logout(HttpServletRequest request){
			HttpSession session=request.getSession(false);
			if(session!=null)
				session.invalidate();
			return "redirect:home.do";
	}
	
	@RequestMapping("idcheckAjax_delivery.do")
	@ResponseBody
	public String idcheckAjax(String id){
		int count=service.idcheck(id);
		return (count==0)? "ok":"fail";
	}
	
}
