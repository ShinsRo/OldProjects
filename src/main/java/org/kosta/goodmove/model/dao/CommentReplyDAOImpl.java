package org.kosta.goodmove.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentReplyDAOImpl implements CommentReplyDAO{
	
	@Resource
	 private SqlSessionTemplate template;
	
	@Override
	public List<CommentReplyVO> getAllCommentReplyList(int cno){
		return template.selectList("commentreply.getAllCommentReplyList",cno);
	}
	@Override
	public void insertNewCommentReply(CommentReplyVO rcvo){
		template.insert("commentreply.insertNewCommentReply",rcvo);
	}
	
}
