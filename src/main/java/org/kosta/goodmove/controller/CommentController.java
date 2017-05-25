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
	 * @param 페이지 번호
	 * @param model
	 * @return 이동될 화면의 경로
	 */
	@RequestMapping("getCommentList.do")
	public String getCommentList(String pageNo, Model model) {	
		model.addAttribute("lvo",commentService.getCommentList(pageNo));
		return "comment/commentList.tiles";
	}
	
	/**
	 * 지역후기의 상세내용
	 * @param 글번호
	 * @return	이동될 화면의 경로, 검색된 결과 vo
	 */
	@RequestMapping("showComment.do")
	public ModelAndView showComment(String cno){
		int clno = Integer.parseInt(cno);
		return new ModelAndView("comment/commentDetail.tiles","cvo",commentService.showComment(clno));
	}
	
	/**
	 * 지역후기 수정 화면으로 이동
	 * @param 글번호
	 * @return	이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */
	@RequestMapping("commentUpdateView.do")
	public ModelAndView commentUpdate(String cno){
		int clno = Integer.parseInt(cno);
		return new ModelAndView("comment/commentUpdate.tiles","cvo",commentService.showCommentNoHit(clno));
	}
	
	/**
	 * 지역후기 수정
	 * @param CommentVO
	 * @return	이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */
	@RequestMapping("commentUpdate.do")
	public ModelAndView commentUpdate(CommentVO cvo){
		commentService.updateBoard(cvo);
		return new ModelAndView("comment/commentDetail.tiles","cvo",commentService.showCommentNoHit(Integer.parseInt(cvo.getCno())));
	}
	
	/**
	 * 지역후기 등록 페이지로 이동
	 * @return	이동될 화면의 경로
	 */
	@RequestMapping("commentRegisterView.do")
	public String commentRehisterView(){
		return "comment/commentRegister.tiles";
	}
	
	/**
	 * 지역후기 등록
	 * @param 로그인 정보를 받아오기 위해 받아온 request
	 * @param 사용자에 의해 작성된 Comment 내용
	 * @return	이동될 화면의 경로, 새로고침 적용되지 않게함, 조회수를 증가하지 않고 검색 시도
	 */
	@RequestMapping(value="commentRegister.do",method=RequestMethod.POST)	
	public ModelAndView write(HttpServletRequest request,CommentVO cvo) {
		HttpSession session=request.getSession(false);
		if(session!=null){
			
			MemberVO mvo=(MemberVO) session.getAttribute("mvo");
			if(mvo!=null){
				cvo.setId(mvo.getId());
				cvo.setAddr(mvo.getAddr());
				
			}
		}		
		commentService.commentRegister(cvo);
		System.out.println(cvo);
		return new ModelAndView("redirect:showCommentNoHit.do?cno="+cvo.getCno());
	}
	
	/**
	 * 조회수를 증가하지 않고 Comment조회
	 * @param 글번호
	 * @return	이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */
	@RequestMapping("showCommentNoHit.do")
	public ModelAndView showCommentNoHit(String cno){
		System.out.println(cno);
	return new ModelAndView("comment/commentDetail.tiles","cvo",commentService.showCommentNoHit(Integer.parseInt(cno)));
}
}
