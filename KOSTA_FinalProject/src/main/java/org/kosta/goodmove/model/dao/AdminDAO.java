package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.ReportPagingBean;
import org.kosta.goodmove.model.vo.ReportVO;

public interface AdminDAO {

	int getTotalReportCount(String category);

	List<ReportVO> getReportList(String category, ReportPagingBean reportPagingBean);

	int getTotalReportCountAll(String category);

	List<ReportVO> getAllReportList(String category, ReportPagingBean reportPagingBean);

	void replyReport(ReportVO rvo);

	void commentReport(ReportVO rvo);

	void deleteReport(int report_no);

	void rejectReport(int report_no);

	void boardReport(ReportVO rvo);


}
