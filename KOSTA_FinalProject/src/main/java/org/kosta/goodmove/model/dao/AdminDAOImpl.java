package org.kosta.goodmove.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.ReportPagingBean;
import org.kosta.goodmove.model.vo.ReportVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO {

	@Resource
	private SqlSessionTemplate template;

	/**
	 * 처리되지 않은 신고갯수 반환
	 */
	@Override
	public int getTotalReportCount(String category) {
		return template.selectOne("admin.getTotalReportCount", category);
	}

	/**
	 * 처리되지 않은 신고리스트 반환
	 */
	@Override
	public List<ReportVO> getReportList(String category, ReportPagingBean reportPagingBean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("category", category);
		map.put("startRowNumber", reportPagingBean.getStartRowNumber());
		map.put("endRowNumber", reportPagingBean.getEndRowNumber());
		return template.selectList("admin.getReportList", map);
	}

	/**
	 * 전체 신고갯수 반환
	 */
	@Override
	public int getTotalReportCountAll(String category) {
		return template.selectOne("admin.getTotalReportCountAll", category);
	}

	/**
	 * 전체 신고리스트 반환
	 */
	@Override
	public List<ReportVO> getAllReportList(String category, ReportPagingBean reportPagingBean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("category", category);
		map.put("startRowNumber", reportPagingBean.getStartRowNumber());
		map.put("endRowNumber", reportPagingBean.getEndRowNumber());
		return template.selectList("admin.getAllReportList", map);
	}

	/**
	 * 댓글 신고
	 */
	@Override
	public void replyReport(ReportVO rvo) {
		template.insert("admin.replyReport", rvo);
	}

	/**
	 * 지역후기 신고
	 */
	@Override
	public void commentReport(ReportVO rvo) {
		template.insert("admin.commentReport", rvo);
	}

	/**
	 * 신고된 글 삭제처리
	 */
	@Override
	public void deleteReport(int report_no) {
		template.update("admin.deleteReport", report_no);
		
	}

	/**
	 * 신고된 글 처리거부
	 */
	@Override
	public void rejectReport(int report_no) {
		template.update("admin.rejectReport", report_no);
	}
	
	/**
	 * 기부글 신고
	 */
	@Override
	public void boardReport(ReportVO rvo) {
		template.insert("admin.boardReport", rvo);
	}

}
