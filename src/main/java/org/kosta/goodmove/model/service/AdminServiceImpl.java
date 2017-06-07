package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.AdminDAO;
import org.kosta.goodmove.model.vo.ReportVO;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminDAO adminDAO;
	
	@Override
	public Object getReportList(String pageNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void replyReport(ReportVO rvo) {
		adminDAO.replyReport(rvo);
	}

	@Override
	public void commentReport(ReportVO rvo) {
		adminDAO.commentReport(rvo);
	}

}
