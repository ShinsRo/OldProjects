package org.kosta.goodmove.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.goodmove.model.service.DeliveryService;
import org.kosta.goodmove.model.vo.DeliveryVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeliveryController {

	@Resource
	private DeliveryService service;
	/**
	 * 용달 사업자 로그인
	 * @param dvo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "login_delivery.do", method = RequestMethod.POST)
	public String login(DeliveryVO dvo, HttpServletRequest request) {
		DeliveryVO vo = service.login(dvo);
		System.out.println(vo);
		if (vo == null) {
			return "member/login_fail.tiles";
		} else {
			request.getSession().setAttribute("dvo", vo);
			return "redirect:home.do";
		}
	}

	/**
	 * 로그아웃에 관한 컨트롤러
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "register_delivery.do", method = RequestMethod.POST)
	public String register(DeliveryVO dvo, String tel1, String tel2, String tel3) {
		dvo.setTel(tel1 + tel2 + tel3);
		service.register(dvo);
		return "redirect:home.do";
	}
	/**
	 * 용달 사업자 로그아웃
	 * @param request
	 * @return
	 */
	@RequestMapping("logout_delivery.do")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		return "redirect:home.do";
	}
	/**
	 * 용달 테이블에 아이디 중복 확인
	 * @param id
	 * @return
	 */
	@RequestMapping("idcheckAjax_delivery.do")
	@ResponseBody
	public String idcheckAjax(String id) {
		int count = service.idcheck(id);
		return (count == 0) ? "ok" : "fail";
	}
	/**
	 * 수락시 is_done 상태 변경
	 * @param id
	 * @param bno
	 * @param currState
	 * @return
	 */
	@RequestMapping("convertDoneState.do")
	public String convertDoneState(String id, String bno, String currState) {
		service.convertDoneState(id, bno, currState);
		return "redirect:getApplicationsById.do";
	}
	/**
	 * 용달 신청한 리스트중 아직 대기중인 신청서들 보여주기
	 * @param model
	 * @return
	 */
	@RequestMapping("getAllDeliveryList.do")
	public String getAllDeliveryList(Model model) {
		model.addAttribute("delivery_list", service.getAllDeliveryList());
		return "delivery/delivery_list.tiles";
	}
	/**
	 * 용달 신청한 신청자,기부자 상세 정보 보여주기
	 * @param id
	 * @param bno
	 * @param model
	 * @return
	 */
	@RequestMapping("getDeliveryDetail.do")
	public String getDeliveryDetail(String id, String bno, Model model) {
		Map<String, MemberVO> map = service.getDeliveryDetail(id, bno);
		model.addAttribute("map", map);
		return "delivery/deliveryDetail";
	}
}
