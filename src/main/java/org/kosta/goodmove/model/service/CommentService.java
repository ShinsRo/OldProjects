package org.kosta.goodmove.model.service;

import java.util.List;

import org.kosta.goodmove.model.vo.CommentListVO;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;

public interface CommentService {

	public  CommentListVO getCommentList();
	public  CommentListVO getCommentList(String pageNo);
	public CommentVO showComment(int clno);
	public CommentVO showCommentNoHit(int clno);
	public void updateBoard(CommentVO cvo);
	void updateCount(int clno);
	public void commentRegister(CommentVO cvo);
	void deleteComment(int cno);
	public int getTotalContentCount();
	List<CommentReplyVO> getAllCommentReplyList(int clno);
	void insertNewCommentReply(CommentReplyVO rcvo);
	int getNextReplyNo();
}
