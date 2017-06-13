package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.QuestionDAO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.kosta.goodmove.model.vo.QuestionListVO;
import org.kosta.goodmove.model.vo.QuestionVO;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Resource
	private QuestionDAO dao;
	
	@Override
	public void registerQuestion(QuestionVO qvo) {
		dao.registerQuestion(qvo);
	}

	@Override
	public void updateQuestion(QuestionVO qvo) {

	}

	@Override
	public void deleteQuestion(int qno) {

	}

	@Override
	public QuestionListVO getQuestionList() {
		return getQuestionList("1");
	}
	@Override
	public QuestionListVO getQuestionList(String pageNo) {
		int totalCount=dao.getTotalQuestionCount();
		PagingBean pagingBean=null;
		if(pageNo==null)
			pagingBean=new PagingBean(totalCount);
		else
			pagingBean=new PagingBean(totalCount,Integer.parseInt(pageNo));		
		return new QuestionListVO(dao.getAllQuestionList(pagingBean),pagingBean);
	}

	@Override
	public QuestionVO showQuestionDetail(String qno) {
		return dao.showQuestionDetail(qno);
	}

	@Override
	public void updateHit(String qno) {
		dao.updateHit(qno);
	}
	
}
