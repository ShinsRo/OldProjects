package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.AdminDAO;
import org.kosta.goodmove.model.vo.ReportListVO;
import org.kosta.goodmove.model.vo.ReportPagingBean;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminDAO adminDAO;
	
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

	@Override
	public int reportCount(String category) {
		return adminDAO.getTotalReportCount(category);
	}

	@Override
	public int reportAllCount(String category) {
		return adminDAO.getTotalReportCountAll(category);
	}
	
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


}
