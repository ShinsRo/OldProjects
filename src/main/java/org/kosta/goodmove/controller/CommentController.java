package org.kosta.goodmove.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.SearchVO;
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
	@Resource
	private SearchService searchService;
	/**
	 * 지역후기 리스트를 받아오는 메서드
	 * 
	 * @param 페이지
	 *            번호
	 * @param model
	 * @return 이동될 화면의 경로
	 */
	@RequestMapping("getCommentList.do")
	public String getCommentList(String pageNo, String id, Model model){
		if(id!=null){
			SearchVO svo = new SearchVO("comment","id",id);
			pageNo = "1";
			model.addAttribute("lvo",searchService.searchComment(svo, pageNo));
		}else{
			model.addAttribute("lvo", commentService.getCommentList(pageNo));
		}
		return "comment/commentList.tiles";
	}

	/**
	 * 지역후기의 상세내용
	 * 
	 * @param 글번호
	 * @return 이동될 화면의 경로, 검색된 결과 vo
	 */
	@RequestMapping("showComment.do")
	public String showComment(String cno, Model model) {
		int clno = Integer.parseInt(cno);
		model.addAttribute("cvo", commentService.showComment(clno));
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(clno));
		return "comment/commentDetail.tiles";
	}

	/**
	 * 지역후기 수정 화면으로 이동
	 * 
	 * @param cno
	 * @return 이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */
	@RequestMapping("commentUpdateView.do")
	public ModelAndView commentUpdate(String cno) {
		int clno = Integer.parseInt(cno);
		return new ModelAndView("comment/commentUpdate.tiles", "cvo", commentService.showCommentNoHit(clno));
	}

	/**
	 * 지역후기 수정
	 * 
	 * @param CommentVO
	 * @return 이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */
	@RequestMapping("commentUpdate.do")
	public ModelAndView commentUpdate(CommentVO cvo) {
		commentService.updateBoard(cvo);
		return new ModelAndView("comment/commentDetail.tiles", "cvo",
				commentService.showCommentNoHit(Integer.parseInt(cvo.getCno())));
	}

	/**
	 * 지역후기 등록 페이지로 이동
	 * 
	 * @return 이동될 화면의 경로
	 */
	@RequestMapping("commentRegisterView.do")
	public String commentRehisterView(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			MemberVO mvo = (MemberVO) session.getAttribute("mvo");
			if (mvo != null) {
				String add = mvo.getAddr();
				if(add.lastIndexOf("길")>0){
					model.addAttribute("add", add.substring(0,add.lastIndexOf("길")+1));
				}else if(add.lastIndexOf("로")<0 && add.lastIndexOf("길")>0){
					model.addAttribute("add", add.substring(0,add.lastIndexOf("로")+1));
				}else{
					model.addAttribute("add", add);
				}
			}
		}
		return "comment/commentRegister.tiles";
	}

	/**
	 * 지역후기 등록
	 * 
	 * @param 로그인
	 *            정보를 받아오기 위해 받아온 request
	 * @param 사용자에
	 *            의해 작성된 Comment 내용
	 * @return 이동될 화면의 경로, 새로고침 적용되지 않게함, 조회수를 증가하지 않고 검색 시도
	 */
	@RequestMapping(value = "commentRegister.do", method = RequestMethod.POST)
	public ModelAndView write(String addr, HttpServletRequest request, CommentVO cvo) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			MemberVO mvo = (MemberVO) session.getAttribute("mvo");
			if (mvo != null) {
				cvo.setId(mvo.getId());
				cvo.setAddr(addr);

			}
		}
		commentService.commentRegister(cvo);
		return new ModelAndView("redirect:showCommentNoHit.do?cno=" + cvo.getCno());
	}

	/**
	 * 조회수를 증가하지 않고 Comment조회
	 * 
	 * @param 글번호
	 * @return 이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */

	@RequestMapping("showCommentNoHit.do")
	public String showCommentNoHit(String cno, Model model) {
		int clno = Integer.parseInt(cno);
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(clno));
		model.addAttribute("cvo", commentService.showCommentNoHit(Integer.parseInt(cno)));
		return "comment/commentDetail.tiles";
	}

	/**
	 * 지역후기 삭제
	 * @param cno
	 * @return
	 */
	@RequestMapping("deleteComment.do")
	public ModelAndView deleteBoard(int cno) {
		commentService.deleteComment(cno);
		return new ModelAndView("comment/commentList.tiles", "lvo", commentService.getCommentList());
	}
	/**
	 * 댓글
	 * @param crvo
	 * @return
	 */
	@RequestMapping("writeCommentReply1.do")
	public String writeCommentReply1(CommentReplyVO crvo) {
		int rno = commentService.getNextReplyNo();
		int gno = rno;
		commentService.insertNewCommentReply(new CommentReplyVO(rno, crvo.getCno(), crvo.getId(), crvo.getName(), crvo.getParent(), crvo.getContent(), gno, crvo.getdepth(), crvo.getOrder_no()));
		return "redirect:showCommentNoHit.do?cno=" + crvo.getCno();
	}

	/**
	 * 대댓글
	 * @param crvo
	 * @return
	 */
	@RequestMapping("writeCommentReply2.do")
	public String writeCommentReply(CommentReplyVO crvo) {
		int rno = commentService.getNextReplyNo();
		crvo.setGno(crvo.getParent());
		CommentReplyVO pvo = commentService.getParentInfo(crvo.getParent());
		if (commentService.getParentsParentId(crvo.getParent()) != 0) {
			crvo.setParent(commentService.getParentsParentId(crvo.getParent()));
			crvo.setGno(crvo.getParent());
			}
		commentService.insertNewCommentReply(new CommentReplyVO(rno, crvo.getCno(), crvo.getId(), crvo.getName(), crvo.getParent(), crvo.getContent(), crvo.getGno(), pvo.getdepth(), pvo.getOrder_no()));
		return "redirect:showCommentNoHit.do?cno=" + crvo.getCno();
	}

	/**
	 * 댓글삭제
	 * @param rno
	 * @param cno
	 * @return
	 */
	@RequestMapping("deleteCommentReply.do")
	public String deleteCommentReply(int rno, int cno) {
		CommentReplyVO crvo = commentService.getCommentReplyInfoByRNO(rno);
		commentService.deleteCommentReply(rno);
		if(crvo.getParent()==0)
		commentService.deleteCommentReplyChild(crvo.getGno());
		return "redirect:showCommentNoHit.do?cno=" + cno;
	}

	/**
	 * 댓글 수정
	 * @param cno
	 * @param rno
	 * @param content
	 * @return
	 */
	@RequestMapping("updateCommentReply.do")
	public String updateCommentReply(int cno, int rno, String content) {
		CommentReplyVO crvo = new CommentReplyVO(rno, content);
		commentService.updateCommentReply(crvo);
		return "redirect:showCommentNoHit.do?&cno=" + cno;
	}
	
	/**
	 * 아이디 이용하여 내가작성한 지역후기 리스트 반환
	 * @param id
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("findCommentListById.do")
	public String findCommentById(String id, String pageNo, Model model ){
		model.addAttribute("lvo", commentService.findCommentListById(id, pageNo));
		return "comment/commentList.tiles";
	}
}
