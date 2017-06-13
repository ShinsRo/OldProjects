package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.QuestionListVO;
import org.kosta.goodmove.model.vo.QuestionVO;

public interface QuestionService {
	public void registerQuestion(QuestionVO qvo);
	public void updateQuestion(QuestionVO qvo);
	public void deleteQuestion(int qno);
	QuestionListVO getQuestionList();
	QuestionListVO getQuestionList(String pageNo);
	QuestionVO showQuestionDetail(String qno);
	void updateHit(String qno);

}
