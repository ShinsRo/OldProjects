package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductSetVO;

public interface BoardService {
	public int getNextBno();
	public int getNextPno();
	public BoardListVO getAllBoardList(String pageNo);
	public BoardListVO getAllBoardList();
	public void boardRegister(BoardVO bvo, ProductSetVO psvo);
}
