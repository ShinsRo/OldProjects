package org.kosta.goodmove.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.DeliveryVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 메타 정보 관할 컨트롤러 : Controller
 * 
 * @author AreadyDoneTeam
 * @version 1
 */
@Controller
public class HomeController {

	@Resource
	SearchService ss;
	/**
	 * 메인 페이지
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("home.do")
	public String home(Model model, HttpServletRequest request) {
		MemberVO mvo = (MemberVO) request.getSession().getAttribute("mvo");
		DeliveryVO dvo = (DeliveryVO) request.getSession().getAttribute("dvo");
		String info;
		if (mvo != null) {
			try {
				int i = InetAddress.getLocalHost().toString().lastIndexOf("/");
				info = mvo.getId() + InetAddress.getLocalHost().toString().substring(i);
				model.addAttribute("count", ss.countday(info));

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				int i = InetAddress.getLocalHost().toString().lastIndexOf("/");
				info = InetAddress.getLocalHost().toString().substring(i);
				model.addAttribute("count", ss.countday(info));

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "home.tiles";
	}

	@RequestMapping("{viewName}.do")
	public String showView(@PathVariable String viewName) {
		// System.out.println("@PathVariable:"+viewName);
		return viewName + ".tiles";

	}

	@RequestMapping("{dirName}/{viewName}.do")
	public String showView(@PathVariable String dirName, @PathVariable String viewName) {
		// System.out.println("@PathVariable:"+dirName+"/"+viewName);
		return dirName + "/" + viewName + ".tiles";
	}
	/**
	 * contact 페이지 이동
	 * @return
	 */
	@RequestMapping("contact.do")
	public String contact() {
		return "contact.tiles";
	}
	/**
	 * admin 페이지 이동
	 * @return
	 */
	@RequestMapping("admin.do")
	public String admin(){
		return "admin.tiles";
	}
}
