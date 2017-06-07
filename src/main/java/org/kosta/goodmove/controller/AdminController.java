package org.kosta.goodmove.controller;

import javax.annotation.Resource;

import org.kosta.goodmove.model.service.AdminService;
import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.DeliveryService;
import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.ReportVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	@Resource
	private CommentService commentService;
	@Resource
	private SearchService searchService;
	@Resource
	private DeliveryService deliveryService;
	@Resource
	private AdminService adminService;

	/**
	 * 관리자모드에서 지역후기 리스트 반환
	 * 
	 * @param pageNo
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("getCommentList_admin.do")
	public String getCommentListAdmin(String pageNo, String id, Model model) {
		model.addAttribute("lvo", commentService.getCommentList(pageNo));
		return "admin/commentList_admin.tiles2";
	}

	/**
	 * 체크박스를 이용하여 한번에 지역후기 지우기
	 * 
	 * @param cno
	 */
	@RequestMapping("deleteCheck.do")
	public void deleteCheck(int cno) {
		commentService.deleteComment(cno);
	}

	/**
	 * 관리자모드에서 지역후기 상세내용보기
	 */
	@RequestMapping("showComment_admin.do")
	public String showCommentAdmin(String cno, Model model) {
		int clno = Integer.parseInt(cno);
		model.addAttribute("cvo", commentService.showComment(clno));
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(clno));
		return "admin/commentDetail_admin.tiles2";
	}

	/**
	 * 관리자모드에서 지역후기 지우기
	 * 
	 * @param cno
	 * @return
	 */
	@RequestMapping("deleteComment_admin.do")
	public ModelAndView deleteBoardAdmin(int cno) {
		commentService.deleteComment(cno);
		return new ModelAndView("admin/commentList_admin.tiles2", "lvo", commentService.getCommentList());
	}

	/**
	 * 관리자모드에서 지역후기 수정화면으로 이동
	 * 
	 * @param cno
	 * @return
	 */
	@RequestMapping("commentUpdateView_admin.do")
	public ModelAndView commentUpdateAdmin(String cno) {
		int clno = Integer.parseInt(cno);
		return new ModelAndView("admin/commentUpdate_admin.tiles2", "cvo", commentService.showCommentNoHit(clno));
	}

	/**
	 * 관리자모드에서 지역후기 수정
	 * 
	 * @param cvo
	 * @return
	 */
	@RequestMapping("commentUpdate_admin.do")
	public ModelAndView commentUpdateAdmin(CommentVO cvo) {
		commentService.updateBoard(cvo);
		return new ModelAndView("admin/commentDetail_admin.tiles2", "cvo",
				commentService.showCommentNoHit(Integer.parseInt(cvo.getCno())));
	}

	/**
	 * 관리자모드에서 댓글 삭제
	 * 
	 * @param rno
	 * @param cno
	 * @return
	 */
	@RequestMapping("deleteCommentReply_admin.do")
	public String deleteCommentReplyAdmin(int rno, int cno) {
		CommentReplyVO crvo = commentService.getCommentReplyInfoByRNO(rno);
		commentService.deleteCommentReply(rno);
		if (crvo.getParent() == 0)
			commentService.deleteCommentReplyChild(crvo.getGno());
		return "redirect:showCommentNoHit_admin.do?cno=" + cno;
	}

	/**
	 * 관리자모드에서 조회수 증가하지 않는 지역후기 보기
	 * 
	 * @param cno
	 * @param model
	 * @return
	 */
	@RequestMapping("showCommentNoHit_admin.do")
	public String showCommentNoHitAdmin(String cno, Model model) {
		int clno = Integer.parseInt(cno);
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(clno));
		model.addAttribute("cvo", commentService.showCommentNoHit(Integer.parseInt(cno)));
		return "admin/commentDetail_admin.tiles2";
	}

	/**
	 * 관리자모드에서 댓글 업데이트
	 * 
	 * @param cno
	 * @param rno
	 * @param rememo
	 * @return
	 */
	@RequestMapping("updateCommentReply_admin.do")
	public String updateCommentReplyAdmin(int cno, int rno, String rememo) {
		CommentReplyVO crvo = new CommentReplyVO(rno, rememo);
		commentService.updateCommentReply(crvo);
		return "redirect:showCommentNoHit_admin.do?&cno=" + cno;
	}

	/**
	 * 관리자 페이지에서 용달 제휴신청 관리
	 * 
	 * @return
	 */
	@RequestMapping("deliveryList_admin.do")
	public String deliveryListAdmin(Model model) {
		model.addAttribute("NotSelectedlist", deliveryService.getNotConfirmedDeliveryList());
		return "admin/deliveryList_admin.tiles2";
	}
	
	@RequestMapping("getReportList_admin.do")
	public String getReportList_admin(String category, String pageNo, Model model ){
		model.addAttribute("lvo", adminService.getReportList(pageNo));
		return "admin/reportList.tiles2";
	}
	
	/**
	 * 신고하기
	 */
	@RequestMapping("reportReply.do")
	public String reortReply(ReportVO rvo, Model model, int cno){
		System.out.println(rvo);
		adminService.replyReport(rvo);
		System.out.println("댓글 신고완료!");
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(cno));
		model.addAttribute("cvo",commentService.showCommentNoHit(cno));
		return "comment/commentDetail.tiles";
	}
	
	@RequestMapping("reportComment.do")
	public String reportComment(ReportVO rvo, Model model){
		System.out.println(rvo);
		adminService.commentReport(rvo);
		System.out.println("후기 신고완료!");
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(rvo.getReno()));
		model.addAttribute("cvo",commentService.showCommentNoHit(rvo.getReno()));
		return "comment/commentDetail.tiles";
	}
}
