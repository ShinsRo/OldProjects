package org.kosta.goodmove.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAOImpl implements CommentDAO{
	@Resource
	 private SqlSessionTemplate template;

	@Override
	public int getTotalContentCount() {
		return template.selectOne("comment.getTotalContentCount");
	}

	@Override
	public List<CommentVO> getCommentList(PagingBean pagingBean) {
		return template.selectList("comment.getCommentList", pagingBean);
	}

	@Override
	public CommentVO showComment(int cno) {
		return template.selectOne("comment.showComment", cno);
	}

	@Override
	public void updateCount(int clno) {
		template.update("comment.updateCount",clno);
	}

	@Override
	public void commentUpdate(CommentVO cvo) {
		template.update("comment.commentUpdate", cvo);
	}

	@Override
	public void commentRegister(CommentVO cvo) {
		template.insert("comment.commentRegister", cvo);
		
	}
}
