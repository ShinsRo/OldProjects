package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.ReportVO;

public interface AdminService {

	Object getReportList(String pageNo);

	void replyReport(ReportVO rvo);

	void commentReport(ReportVO rvo);

}
