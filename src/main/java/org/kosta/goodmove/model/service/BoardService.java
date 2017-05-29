package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardVO;

public interface BoardService {
	public int getNextBno();
	public BoardListVO getAllBoardList(String pageNo);
	public BoardListVO getAllBoardList();
	BoardVO getBoardDetailByBno(int bno);
}
