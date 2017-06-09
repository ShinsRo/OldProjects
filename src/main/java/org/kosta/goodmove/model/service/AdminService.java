package org.kosta.goodmove.model.service;


import org.kosta.goodmove.model.vo.ReportListVO;

import org.kosta.goodmove.model.vo.ReportVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminService {

	public ReportListVO getReportList(String pageNo, String category);

	public int reportCount(String category);

	public int reportAllCount(String category);

	public ReportListVO getAllReportList(String pageNo, String category);

	void replyReport(ReportVO rvo);

	void commentReport(ReportVO rvo);

}
