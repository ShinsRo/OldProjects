package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.AdminDAO;
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

}
