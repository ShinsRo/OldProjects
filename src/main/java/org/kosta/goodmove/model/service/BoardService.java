package org.kosta.goodmove.model.service;

import java.util.List;

import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductSetVO;
import org.kosta.goodmove.model.vo.ProductVO;

public interface BoardService {
	public int getNextBno();
	public int getNextPno();
	public BoardListVO getAllBoardList(String pageNo);
	public BoardListVO getAllBoardList();
	public int getCountBoard();
	public void boardRegister(BoardVO bvo, ProductSetVO psvo);
	public BoardVO getBoardDetailByBno(int bno);
	List<ProductVO> getProductImgByBno(int bno);
	public BoardListVO getMyBoardList(String pageNo, String id);

}
