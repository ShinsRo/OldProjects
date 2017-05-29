package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.BoardListVO;

public interface BoardService {
	public int getNextBno();
	public BoardListVO getAllBoardList(String pageNo);
	public BoardListVO getAllBoardList();
	public int getCountBoard();
}
