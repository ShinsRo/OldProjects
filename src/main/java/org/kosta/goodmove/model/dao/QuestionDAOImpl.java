package org.kosta.goodmove.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.PagingBean;
import org.kosta.goodmove.model.vo.QuestionVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDAOImpl implements QuestionDAO {
	
	@Resource
	 private SqlSessionTemplate template;

	@Override
	public void registerQuestion(QuestionVO qvo) {
		template.insert("question.registerQuestion", qvo);
	}

	@Override
	public void updateQuestion(QuestionVO qvo) {

	}

	@Override
	public void deleteQuestion(int qno) {

	}

	@Override
	public List<QuestionVO> getAllQuestionList(PagingBean pagingBean) {
		return template.selectList("question.getQuestionList", pagingBean);
	}
	@Override
	public int getTotalQuestionCount(){
		return template.selectOne("question.getTotalQuestionCount");
	}

	@Override
	public QuestionVO showQuestionDetail(String qno) {
		return template.selectOne("question.showQuestionDetail", qno);
	}

	@Override
	public void updateHit(String qno) {
		template.update("question.updateHit",qno);
	}

}
