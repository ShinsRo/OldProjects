package org.kosta.goodmove.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 후기 정보 관할 컨트롤러 : Controller
 * @author AreadyDoneTeam
 * @version 1
 */
import org.springframework.web.servlet.ModelAndView;
@Controller
public class CommentController {

	@Resource
	private CommentService commentService;
	
	/**
	 * 지역후기 리스트를 받아오는 메서드
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("getCommentList.do")
	public String getCommentList(String pageNo, Model model) {	
		model.addAttribute("lvo",commentService.getCommentList(pageNo));
		return "comment/commentList.tiles";
	}
	
	/**
	 * 지역후기의 상세내용
	 * @param cno
	 * @return
	 */
	@RequestMapping("showComment.do")
	public ModelAndView showComment(String cno){
		int clno = Integer.parseInt(cno);
		return new ModelAndView("comment/commentDetail.tiles","cvo",commentService.showComment(clno));
	}
	
	/**
	 * 지역후기 수정 화면으로 이동
	 * @param cno
	 * @return
	 */
	@RequestMapping("commentUpdateView.do")
	public ModelAndView commentUpdate(String cno){
		int clno = Integer.parseInt(cno);
		return new ModelAndView("comment/commentUpdate.tiles","cvo",commentService.showCommentNoHit(clno));
	}
	
	/**
	 * 지역후기 수정
	 * @param cvo
	 * @return
	 */
	@RequestMapping("commentUpdate.do")
	public ModelAndView commentUpdate(CommentVO cvo){
		commentService.updateBoard(cvo);
		return new ModelAndView("comment/commentDetail.tiles","cvo",commentService.showCommentNoHit(Integer.parseInt(cvo.getCno())));
	}
	
	@RequestMapping("commentRegisterView.do")
	public String commentRehisterView(){
		return "comment/commentRegister.tiles";
	}
	
	@RequestMapping(value="commentRegister.do",method=RequestMethod.POST)	
	public ModelAndView write(HttpServletRequest request,CommentVO cvo) {
		HttpSession session=request.getSession(false);
		if(session!=null){
			MemberVO mvo=(MemberVO) session.getAttribute("mvo");
			if(mvo!=null){
				cvo.setId(mvo.getId());
				cvo.setAddr(mvo.getAddr_code());
			}
		}		
		commentService.commentRegister(cvo);
		return new ModelAndView("redirect:showCommentNoHit.do?no="+cvo.getCno());
	}
}
