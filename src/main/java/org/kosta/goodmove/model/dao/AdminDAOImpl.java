package org.kosta.goodmove.model.dao;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.ReportVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO {

	@Resource
	private SqlSessionTemplate template;
	
	
	@Override
	public void replyReport(ReportVO rvo) {
		template.insert("admin.replyReport",rvo);
	}
	
	@Override
	public void commentReport(ReportVO rvo){
		template.insert("admin.commentReport",rvo);
	}
}
