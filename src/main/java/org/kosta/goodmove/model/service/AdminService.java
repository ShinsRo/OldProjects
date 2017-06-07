package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.ReportListVO;

public interface AdminService {

	public ReportListVO getReportList(String pageNo, String category);

	public int reportCount(String category);

	public int reportAllCount(String category);

	public ReportListVO getAllReportList(String pageNo, String category);

}
