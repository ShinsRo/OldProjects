package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.CommentReplyVO;

public interface CommentReplyService {

	Object getAllCommentReplyList(int clno);

	void insertNewCommentReply(CommentReplyVO rcvo);

}
