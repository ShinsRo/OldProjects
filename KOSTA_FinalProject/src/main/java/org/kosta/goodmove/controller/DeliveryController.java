package org.kosta.goodmove.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.service.DeliveryService;
import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeliveryController {

	@Resource
	private DeliveryService service;
	@Resource
	private BoardService boardService;

	/**
	 * 배송기사 권한 추가
	 * @return
	 */
	@Secured("ROLE_MEMBER")
	@RequestMapping("coalition.do")
	public String coalition(){
		MemberVO mvo = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		service.coalition(mvo);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_DEL")); //add your role here [e.g., new SimpleGrantedAuthority("ROLE_NEW_ROLE")]
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		return "redirect:home.do";
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
		List<ApplicationVO> list = service.getAllDeliveryList();
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		model.addAttribute("delivery_list", service.getAllDeliveryList());
		return "delivery/delivery_list.tiles";
	}
	/**
	 * 용달 아이디별로 자신의 용달 매칭 내역 보기
	 * @param did
	 * @param model
	 * @return
	 */
	@RequestMapping("getDeliveryListByDid.do")
	public String getDeliveryListByDid(Model model,HttpServletRequest req){
		MemberVO dvo = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//String did = ((DeliveryVO) req.getSession(false).getAttribute("dvo")).getId();
		String did = dvo.getId();
		model.addAttribute("my_list", service.findDeliveryMatchByDid(did));
		return "delivery/my_delivery_list.tiles";
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
	/**
	 * 용달 사업자가 신청서를 채택하면
	 * delivery_math 테이블에 insert시키고
	 * 신청 리스트에서 제외한다.
	 * @param bno
	 * @param aid
	 * @param did
	 * @return
	 */
	@RequestMapping("registerDeliveryMatch.do")
	public String registerDeliveryMatch(String bno, String aid, String did){
		service.registerDeliveryMatch(bno, aid, did);
		return "redirect:getAllDeliveryList.do";
	}
}
