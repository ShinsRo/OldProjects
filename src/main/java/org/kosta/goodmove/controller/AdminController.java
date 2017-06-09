package org.kosta.goodmove.controller;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.service.AdminService;
import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.DeliveryService;
import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.ReportVO;
import org.kosta.goodmove.model.vo.ProductVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	@Resource
	private BoardService boardService;

	/**
	 * 관리자모드에서 지역후기 리스트 반환
	 * 
	 * @param pageNo
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("getCommentList_admin.do")
	public ModelAndView getCommentListAdmin(String pageNo, String id, Model model) {
		return new ModelAndView("admin/commentList_admin.tiles2", "lvo", commentService.getCommentList(pageNo));
	}

	/**
	 * 체크박스를 이용하여 한번에 지역후기 지우기
	 * 
	 * @param cno
	 */
	@RequestMapping("deleteCheck_admin.do")
	public void deleteCheck(int cno) {
		commentService.deleteComment(cno);
	}

	/**
	 * 관리자모드에서 지역후기 상세내용보기
	 * 
	 * @param cno
	 * @param model
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
	 * 관리자 상품리스트 반환
	 * 
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("getBoardList_admin.do")
	public String getBoardList_admin(String pageNo, Model model) {
		BoardListVO blvo = boardService.getAllBoardList(pageNo);
		model.addAttribute("blvo", blvo);
		return "admin/boardList_admin.tiles2";
	}

	/**
	 * 관리자 기부글 관리
	 * 
	 * @param bno
	 * @param model
	 * @return
	 */
	@RequestMapping("boardDetail_admin.do")
	public String boardDetail_admin(String bno, Model model) {
		BoardVO bvo = boardService.getBoardDetailByBno(Integer.parseInt(bno));
		List<ProductVO> plist = boardService.getProductImgByBno(Integer.parseInt(bno));
		model.addAttribute("bvo", bvo);
		model.addAttribute("plist", plist);
		return "admin/boardDetail_admin.tiles2";
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

	/**
	 * 처리 완료되지 않은 신고내역 반환
	 * 
	 * @param category
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("getReportList_admin.do")
	public String getReportList_admin(String category, String pageNo, Model model) {
		if (category == null)
			category = "board";
		model.addAttribute("lvo", adminService.getReportList(pageNo, category));
		model.addAttribute("type", category);
		model.addAttribute("count", adminService.reportCount());
		return "admin/reportList.tiles2";
	}

	/**
	 * 모든 신고내역 반환
	 * 
	 * @param category
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("getAllReportList_admin.do")
	public String getAllReportList_admin(String category, String pageNo, Model model) {
		model.addAttribute("lvo", adminService.getAllReportList(pageNo, category));
		model.addAttribute("type", category + "All");
		model.addAttribute("count", adminService.reportCount());
		return "admin/reportList.tiles2";
	}

	/**
	 * 댓글 신고하기
	 * 
	 * @param rvo
	 * @param model
	 * @param cno
	 * @return
	 */
	@RequestMapping("reportReply_admin.do")
	public String reortReply(ReportVO rvo, Model model, int cno){
		adminService.replyReport(rvo);
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(cno));
		model.addAttribute("cvo", commentService.showCommentNoHit(cno));
		return "comment/commentDetail.tiles";
	}
	
	/**
	 * 후기 신고하기
	 * 
	 * @param rvo
	 * @param model
	 * @return
	 */
	@RequestMapping("reportComment_admin.do")
	public String reportComment(ReportVO rvo, Model model){

		adminService.commentReport(rvo);
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(rvo.getReno()));
		model.addAttribute("cvo", commentService.showCommentNoHit(rvo.getReno()));
		return "comment/commentDetail.tiles";
	}

	/**
	 * 기부글 신고
	 * 
	 * @param rvo
	 * @param model
	 * @return
	 */
	@RequestMapping("reportBoard.do")
	public String reportBoard(ReportVO rvo, Model model) {
		adminService.boardReport(rvo);
		model.addAttribute("bvo", boardService.getBoardDetailByBno(rvo.getReno()));
		model.addAttribute("plist", boardService.getProductImgByBno(rvo.getReno()));
		return "board/boardDetail.tiles";

	}

	/**
	 * 용달 제휴 승인
	 * @param id
	 * @return
	 */
@RequestMapping("confirmDelivery_admin.do")
	public String confirmDelivery(String id) {
		deliveryService.confirmDelivery(id);
		return "redirect:deliveryList_admin.do";
	}

	/**
	 * 관리자에서 기부글 삭제
	 * 
	 * @param bno
	 * @return
	 */
	@RequestMapping("boardDelete_admin.do")
	public String boardDelete_admin(String bno) {
		boardService.delete(bno);
		return "redirect:admin/deleteResult_admin.do";
	}

	/**
	 * 신고된 글 삭제처리
	 * 
	 * @param category
	 * @param reno
	 * @param report_no
	 * @param type
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("deleteReport_admin.do")
	public String deleteReport(String category, int reno, int report_no, String type, int pageNo) {
		adminService.deleteObj(category, reno);
		adminService.deleteReport(report_no);
		if (type.lastIndexOf("All") == -1) {
			return "redirect:getReportList_admin.do?category=" + category + "&pageNo=" + pageNo;
		} else {
			return "redirect:getAllReportList_admin.do?category=" + category + "&pageNo=" + pageNo;
		}
	}

	/**
	 * 신고된 글 처리거부
	 * 
	 * @param category
	 * @param reno
	 * @param report_no
	 * @param type
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("rejectReport_admin.do")
	public String rejectReport(String category, int reno, int report_no, String type, int pageNo) {
		adminService.rejectReport(report_no);
		if (type.lastIndexOf("All") == -1) {
			return "redirect:getReportList_admin.do?category=" + category + "&pageNo=" + pageNo;
		} else {
			return "redirect:getAllReportList_admin.do?category=" + category + "&pageNo=" + pageNo;
		}
	}

	/**
	 * 신고된 글 상세내용
	 * 
	 * @param category
	 * @param reno
	 * @param report_no
	 * @param type
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("showReport_admin.do")
	public ModelAndView showReport_admin(String category, int reno, int report_no, String type, int pageNo) {
		return new ModelAndView("admin/reportDitail_admin.tiles2", "show", adminService.showReport(category, reno, report_no, type, pageNo));
	}
	
	/**
	 * 관리자 메인페이지에 출력될 신고갯수 반환
	 * @param category
	 * @return
	 */
	@RequestMapping("CountReport_admin.do")
	@ResponseBody
	public int CountReport_admin(String category) {
		return adminService.getTotalReportCount(category);
	}
	
	/**
	 * 관리자 메인페이지에 출력될 제휴 미승인 대기자수 반환
	 * @return
	 */
	@RequestMapping("CountDelivery_admin.do")
	@ResponseBody
	public int CountDelivery_admin() {
		return deliveryService.countDelivery_admin();
	}
}
