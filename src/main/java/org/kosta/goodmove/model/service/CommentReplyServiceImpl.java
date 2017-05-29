package org.kosta.goodmove.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.CommentReplyDAO;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.springframework.stereotype.Service;

@Service
public class CommentReplyServiceImpl implements CommentReplyService{
	@Resource
	private CommentReplyDAO commentReplyDAO;
	
	@Override
	public List<CommentReplyVO> getAllCommentReplyList(int clno) {
		return commentReplyDAO.getAllCommentReplyList(clno);
	}
	@Override
	public void insertNewCommentReply(CommentReplyVO rcvo){
		commentReplyDAO.insertNewCommentReply(rcvo);
	}
}
