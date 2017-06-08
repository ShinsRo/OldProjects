package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.AdminDAO;
import org.kosta.goodmove.model.vo.ReportListVO;
import org.kosta.goodmove.model.vo.ReportPagingBean;
import org.kosta.goodmove.model.vo.ReportVO;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminDAO adminDAO;
	
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
	public int reportCount(String category) {
		return adminDAO.getTotalReportCount(category);
	}

	/**
	 * 모든 신고갯수 받아오기
	 */
	@Override
	public int reportAllCount(String category) {
		return adminDAO.getTotalReportCountAll(category);
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


}
