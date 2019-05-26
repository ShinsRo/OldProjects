package org.kosta.goodmove.model.service;


import java.util.HashMap;

import org.kosta.goodmove.model.vo.ReportListVO;

import org.kosta.goodmove.model.vo.ReportVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminService {

	public ReportListVO getReportList(String pageNo, String category);

	public HashMap<String, Integer> reportCount();

	public ReportListVO getAllReportList(String pageNo, String category);

	void replyReport(ReportVO rvo);

	void commentReport(ReportVO rvo);

	public void deleteReport(int report_no);

	public void rejectReport(int report_no);
	
	void boardReport(ReportVO rvo);

	public void deleteObj(String category, int reno);

	public HashMap<String, Object> showReport(String category, int reno, int report_no, String type, int pageNo);

	public int getTotalReportCount(String category);


}
