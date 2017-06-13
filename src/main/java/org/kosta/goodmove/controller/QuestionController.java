package org.kosta.goodmove.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.goodmove.model.service.QuestionService;
import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.QuestionVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuestionController {

	@Resource
	private QuestionService service;
	
	@RequestMapping("getQuestionList.do")
	public String getQuestionList(String pageNo, Model model){
		model.addAttribute("lvo", service.getQuestionList(pageNo));
		return "QandA/qaList.tiles";
		
	}
	/**
	 * Q&A 작성 뷰로 이동
	 * @return
	 */
	@RequestMapping("registerQuestionView.do")
	public String registerQuestionView(){
		return "QandA/qaRegister.tiles";
	}
	/**
	 * Q&A 작성
	 * @param qvo
	 * @return
	 */
	@RequestMapping("registerQuestion.do")
	public String registerQuestion(QuestionVO qvo, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		MemberVO mvo = (MemberVO) session.getAttribute("mvo");
		qvo.setId(mvo.getId());
		if(qvo.getIs_secret()==null){ // 비밀글 안했을 때
			qvo.setIs_secret("0");
		}
		service.registerQuestion(qvo);
		String qno = qvo.getId();
		return "redirect:showQuestionNoHit.do?qno="+qno;
	}
	/**
	 * 
	 * @param qno
	 * @return
	 */
	
	@RequestMapping("showQuestionNoHit.do")
	public String showQuestionNoHit(String qno, Model model){
		model.addAttribute("qvo", service.showQuestionNoHit(Integer.parseInt(qno)));
		return "QandA/qaDetail.tiles";
	}
	@RequestMapping("showQuestionHit.do")
	public String showQuestionDetail(String qno, Model model){
		model.addAttribute("qvo", service.showQuestionHit(Integer.parseInt(qno)));
		return "QandA/qaDetail.tiles";
	}
	@RequestMapping("updateQuestionView.do")
	public String updateQuestionView(String qno,Model model){
		model.addAttribute("qvo", service.showQuestionNoHit(Integer.parseInt(qno)));
		return "QandA/qaUpdate.tiles";
	}
	/**
	 * Q&A 수정
	 * @param qno
	 * @param model
	 * @return
	 */
	@RequestMapping("updateQuestion.do")
	public String updateQuestion(QuestionVO qvo,Model model){
		if(qvo.getIs_secret()==null){ // 비밀글 안했을 때
			qvo.setIs_secret("0");
		}else{
			qvo.setIs_secret("1");
		}
		service.updateQuestion(qvo);
		return "redirect:showQuestionNoHit.do?qno="+qvo.getQno();
	}
	@RequestMapping("deleteQuestion.do")
	public String deleteQuestion(String qno){
		service.deleteQuestion(Integer.parseInt(qno));
		return "redirect:getQuestionList.do";
	}
}
