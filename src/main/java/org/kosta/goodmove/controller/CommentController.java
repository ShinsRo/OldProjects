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
		return new ModelAndView("comment/commentList", "lvo", commentService.getCommentList());
	}

	/**
	 * 지역후기 댓글작성
	 * @param model
	 * @param parent
	 * @param reFlag
	 * @param cno
	 * @param rememo
	 * @param request
	 * @return
	 */
	@RequestMapping("writeCommentReply.do")
	public String writeCommentReply(Model model, int parent, String reFlag, int cno, String rememo,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		MemberVO mvo = (MemberVO) session.getAttribute("mvo");
		if (reFlag.equals("true")) {
			int reparent = Integer.parseInt(request.getParameter("reparent"));
			parent = reparent;
		} else {
			parent = Integer.parseInt(request.getParameter("parent"));
		}
		String id = mvo.getId();
		String name = mvo.getName();
		String content = rememo;
		CommentReplyVO pvo = null;
		int rno = commentService.getNextReplyNo();
		int gno = 1;
		int depth = 0;
		int order_no = 0;
		if (parent == 0) { // 글에 댓글 달때
			gno = rno;
			depth = 0;
			order_no = 1;
		} else { // 대댓글 달기
			gno = parent;
			pvo = commentService.getParentInfo(parent);
			depth = pvo.getdepth();
			order_no = pvo.getOrder_no();
			if (commentService.getParentsParentId(parent) != 0) {
				parent = commentService.getParentsParentId(parent);
				gno = parent;
			}
		}
		CommentReplyVO crvo = new CommentReplyVO(rno, cno, id, name, parent, content, gno, depth, order_no);
		commentService.insertNewCommentReply(crvo);
		return "redirect:showCommentNoHit.do?cno=" + cno;
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
	 * @param rememo
	 * @return
	 */
	@RequestMapping("updateCommentReply.do")
	public String updateCommentReply(int cno, int rno, String rememo) {
		CommentReplyVO crvo = new CommentReplyVO(rno, rememo);
		commentService.updateCommentReply(crvo);
		return "redirect:showCommentNoHit.do?&cno=" + cno;
	}
}
