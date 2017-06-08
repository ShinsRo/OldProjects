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

	@Override
	public int getTotalReportCount(String category) {
		return template.selectOne("admin.getTotalReportCount", category);
	}

	@Override
	public List<ReportVO> getReportList(String category, ReportPagingBean reportPagingBean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("category", category);
		map.put("startRowNumber", reportPagingBean.getStartRowNumber());
		map.put("endRowNumber", reportPagingBean.getEndRowNumber());
		return template.selectList("admin.getReportList", map);
	}

	@Override
	public int getTotalReportCountAll(String category) {
		return template.selectOne("admin.getTotalReportCountAll", category);
	}

	@Override
	public List<ReportVO> getAllReportList(String category, ReportPagingBean reportPagingBean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("category", category);
		map.put("startRowNumber", reportPagingBean.getStartRowNumber());
		map.put("endRowNumber", reportPagingBean.getEndRowNumber());
		return template.selectList("admin.getAllReportList", map);
	}

	@Override
	public void replyReport(ReportVO rvo) {
		template.insert("admin.replyReport", rvo);
	}

	@Override
	public void commentReport(ReportVO rvo) {
		template.insert("admin.commentReport", rvo);
	}

	@Override
	public void deleteReport(int report_no) {
		template.update("admin.deleteReport", report_no);
		
	}

	@Override
	public void rejectReport(int report_no) {
		template.update("admin.rejectReport", report_no);
		
	}
}
