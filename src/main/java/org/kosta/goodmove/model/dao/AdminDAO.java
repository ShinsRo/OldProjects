package org.kosta.goodmove.model.dao;

import org.kosta.goodmove.model.vo.ReportVO;

public interface AdminDAO {

	void replyReport(ReportVO rvo);

	void commentReport(ReportVO rvo);

}
