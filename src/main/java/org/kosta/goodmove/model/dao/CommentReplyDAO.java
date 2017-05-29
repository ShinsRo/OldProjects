package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.CommentReplyVO;

public interface CommentReplyDAO {

	List<CommentReplyVO> getAllCommentReplyList(int cno);

	void insertNewCommentReply(CommentReplyVO rcvo);

}
