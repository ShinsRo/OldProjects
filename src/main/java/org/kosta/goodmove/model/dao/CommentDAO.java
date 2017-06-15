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
	CommentReplyVO getCommentReplyInfoByRNO(int rno);
	void deleteCommentReplyChild(int gno);
	List<CommentVO> findCommentById(String id, PagingBean pb);
	int getTotalContentCountById(String id);
	CommentReplyVO showReply(int reno);
	String getPicNo();
	void stackImg(String img_path, String picNo);
	void clickLikeBtn(String cno, String id);
	int findLikeById(String cno, String id);
	int getCountLikeByCno(String cno);
	void unclickLikeBtn(String cno,String id);
}
