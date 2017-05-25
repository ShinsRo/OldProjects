package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.CommentListVO;
import org.kosta.goodmove.model.vo.CommentVO;

public interface CommentService {

	public  CommentListVO getCommentList();
	public  CommentListVO getCommentList(String pageNo);
	public CommentVO showComment(int clno);
	public CommentVO showCommentNoHit(int clno);
	public void updateBoard(CommentVO cvo);
	void updateCount(int clno);
	public void commentRegister(CommentVO cvo);
}
