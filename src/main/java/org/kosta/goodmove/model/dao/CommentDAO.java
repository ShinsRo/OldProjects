package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.kosta.goodmove.model.vo.SearchVO;

public interface CommentDAO {

	int getTotalContentCount();
	public List<CommentVO> getCommentList(PagingBean pagingBean);
	CommentVO showComment(int cno);
	void updateCount(int clno);
	void commentUpdate(CommentVO cvo);
	void commentRegister(CommentVO cvo);
	int getSearchContentCount(SearchVO svo);
	void deleteComment(int cno);
	List<CommentReplyVO> getAllCommentReplyList(int cno);
	void insertNewCommentReply(CommentReplyVO rcvo);
	int getNextReplyNo();
	CommentReplyVO getParentInfo(int parent);
	int getParentsParentId(int parent);
	void deleteCommentReply(int rno);
	void updateCommentReply(CommentReplyVO crvo);
}
