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
		if(qvo.getIs_secret()==null){ // 비밀글 안했을 때
			qvo.setIs_secret("0");
		}
		dao.registerQuestion(qvo);
	}

	@Override
	public void updateQuestion(QuestionVO qvo) {
		dao.updateQuestion(qvo);
	}

	@Override
	public void deleteQuestion(int qno) {
		dao.deleteQuestion(qno);
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
	public QuestionVO showQuestionNoHit(int qno) {
		return dao.showQuestionDetail(qno);
	}
	@Override
	public QuestionVO showQuestionHit(int qno) {
		dao.updateHit(qno);
		return dao.showQuestionDetail(qno);
	}

	@Override
	public void updateHit(int qno) {
		dao.updateHit(qno);
	}
	@Override
	public int getNextQno(){
		return dao.getNextQno();
	}

	@Override
	public void registerAnswer(QuestionVO qvo) {
		if(qvo.getIs_secret()==null){ // 비밀글 안했을 때
			qvo.setIs_secret("0");
		}else{
			qvo.setIs_secret("1");
		}
		dao.registerAnswer(qvo);
	}

	@Override
	public int getParentReRef(int qno) {
		return dao.getParentReRef(qno);
	}


}
