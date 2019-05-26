package org.kosta.goodmove.model.service;

import java.util.HashMap;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.AdminDAO;
import org.kosta.goodmove.model.dao.BoardDAO;
import org.kosta.goodmove.model.dao.CommentDAO;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.ReportListVO;
import org.kosta.goodmove.model.vo.ReportPagingBean;
import org.kosta.goodmove.model.vo.ReportVO;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminDAO adminDAO;
	
	@Resource
	private BoardDAO boardDAO;
	
	@Resource
	private CommentDAO commentDAO;
	
	/**
	 * 처리되지 않은 신고 내용 가져오기
	 */
	@Override
	public ReportListVO getReportList(String pageNo, String category) {
		int totalCount=adminDAO.getTotalReportCount(category);
		
		ReportPagingBean reportPagingBean=null;
		if(pageNo==null)
			reportPagingBean=new ReportPagingBean(totalCount);
		else
			reportPagingBean=new ReportPagingBean(totalCount,Integer.parseInt(pageNo));		
	
		return new ReportListVO(adminDAO.getReportList(category, reportPagingBean),reportPagingBean);
	}
	
	/**
	 * 댓글 신고
	 */
	@Override
	public void replyReport(ReportVO rvo) {
		adminDAO.replyReport(rvo);
	}

	/**
	 * 지역후기 신고
	 */
	@Override
	public void commentReport(ReportVO rvo) {
		adminDAO.commentReport(rvo);
	}
	
	/**
	 * 기부글 신고
	 */
	@Override
	public void boardReport(ReportVO rvo) {
		adminDAO.boardReport(rvo);
	}
	
	/**
	 * 처리되지 않은 신고갯수 받아오기
	 */
	@Override
	public HashMap<String, Integer> reportCount() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("reportBoardCount", adminDAO.getTotalReportCount("board"));
		map.put("reportCommentCount", adminDAO.getTotalReportCount("comment"));
		map.put("reportReplyCount", adminDAO.getTotalReportCount("reply"));
		map.put("reportAllBoardCount", adminDAO.getTotalReportCountAll("board"));
		map.put("reportAllCommentCount", adminDAO.getTotalReportCountAll("comment"));
		map.put("reportAllReplyCount", adminDAO.getTotalReportCountAll("reply"));
		return map;
	}
	
	/**
	 * 모든 신고리스트 받아오기
	 */
	@Override
	public ReportListVO getAllReportList(String pageNo, String category) {
		int totalCount=adminDAO.getTotalReportCountAll(category);
		
		ReportPagingBean reportPagingBean=null;
		if(pageNo==null)
			reportPagingBean=new ReportPagingBean(totalCount);
		else
			reportPagingBean=new ReportPagingBean(totalCount,Integer.parseInt(pageNo));		
	
		return new ReportListVO(adminDAO.getAllReportList(category, reportPagingBean),reportPagingBean);
	}

	/**
	 * 신고된 내용 삭제처리
	 */
	@Override
	public void deleteReport(int report_no) {
		adminDAO.deleteReport(report_no);
		
	}

	/**
	 * 신고된 내용 처리거부
	 */
	@Override
	public void rejectReport(int report_no) {
		adminDAO.rejectReport(report_no);
	}

	/**
	 * 신고된 글 삭제처리
	 */
	@Override
	public void deleteObj(String category, int reno) {
		if (category.equals("board")) {
			boardDAO.deleteBoard(Integer.toString(reno));
		} else if (category.equals("comment")) {
			commentDAO.deleteComment(reno);
		} else {
			CommentReplyVO crvo = commentDAO.getCommentReplyInfoByRNO(reno);
			commentDAO.deleteCommentReply(reno);
			if (crvo.getParent() == 0)
				commentDAO.deleteCommentReplyChild(crvo.getGno());
		}
	}

	/**
	 * 신고글 상세보기
	 */
	@Override
	public HashMap<String, Object> showReport(String category, int reno, int report_no, String type, int pageNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (category.equals("board")) {
			map.put("revo", boardDAO.getBoardDetailByBno(Integer.parseInt(Integer.toString(reno))));
			map.put("plist", boardDAO.getProductImgByBno(Integer.parseInt(Integer.toString(reno))));
		} else if (category.equals("comment")) {
			map.put("revo", commentDAO.showComment(reno));
		} else {
			map.put("revo", commentDAO.showReply(reno));
		}
		map.put("category", category);
		map.put("reno", reno);
		map.put("report_no", report_no);
		map.put("type", type);
		map.put("pageNo", pageNo);
		return map;
	}

	/**
	 * 관리자 메인화면에 출력될 신고갯수 반환
	 */
	@Override
	public int getTotalReportCount(String category) {
		return adminDAO.getTotalReportCount(category);
	}


}
