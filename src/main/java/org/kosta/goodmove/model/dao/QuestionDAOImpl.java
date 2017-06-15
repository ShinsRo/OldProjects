package org.kosta.goodmove.model.dao;

import java.util.HashMap;
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
		template.update("question.updateQuestion", qvo);
	}

	@Override
	public void deleteQuestion(int qno) {
		template.delete("question.deleteQuestion", qno);
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
	public QuestionVO showQuestionDetail(int qno) {
		return template.selectOne("question.showQuestionDetail", qno);
	}

	@Override
	public void updateHit(int qno) {
		template.update("question.updateHit",qno);
	}

	@Override
	public int getNextQno() {
		return template.selectOne("question.getNextQno");
	}

	@Override
	public void registerAnswer(QuestionVO qvo) {
		template.insert("question.registerAnswer", qvo);
	}
	@Override
	public int getParentReRef(int qno){
		return template.selectOne("question.getParentReRef",qno);
	}
}
