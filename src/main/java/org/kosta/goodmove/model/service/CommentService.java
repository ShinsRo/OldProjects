package org.kosta.goodmove.model.service;

import java.util.List;

import org.kosta.goodmove.model.vo.CommentListVO;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentService {

	public  CommentListVO getCommentList();
	public  CommentListVO getCommentList(String pageNo);
	public CommentVO showComment(int clno);
	public CommentVO showCommentNoHit(int clno);
	public void updateBoard(CommentVO cvo);
	public void updateCount(int clno);
	public void commentRegister(CommentVO cvo);
	public void deleteComment(int cno);
	public int getTotalContentCount();
	public List<CommentReplyVO> getAllCommentReplyList(int clno);
	public void insertNewCommentReply(CommentReplyVO rcvo);
	public int getNextReplyNo();
	public CommentReplyVO getParentInfo(int parent);
	public int getParentsParentId(int parent);
	public void deleteCommentReply(int rno);
	public void updateCommentReply(CommentReplyVO crvo);
	public CommentReplyVO getCommentReplyInfoByRNO(int rno);
	public void deleteCommentReplyChild(int gno);
	public CommentListVO findCommentListById(String id, String pageNo);
	public CommentReplyVO showReply(int reno);
	public String getPicNo();
	public void stackImg(String img_path, String picNo);
}
