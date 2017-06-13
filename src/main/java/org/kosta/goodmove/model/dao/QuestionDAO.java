package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.PagingBean;
import org.kosta.goodmove.model.vo.QuestionVO;

public interface QuestionDAO {
	public void registerQuestion(QuestionVO qvo);
	public void updateQuestion(QuestionVO qvo);
	public void deleteQuestion(int qno);
	public List<QuestionVO> getAllQuestionList(PagingBean pagingBean);
	int getTotalQuestionCount();
	public QuestionVO showQuestionDetail(String qno);
	void updateHit(String qno);
}
