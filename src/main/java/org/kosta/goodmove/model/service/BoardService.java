package org.kosta.goodmove.model.service;

import java.util.List;

import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductSetVO;
import org.kosta.goodmove.model.vo.ProductVO;
import org.kosta.goodmove.model.vo.TransactionVO;

public interface BoardService {
	public int getNextBno();

	public int getNextPno();

	public int getNextAno();

	public int getNextTno();

	public BoardListVO getAllBoardList(String pageNo);

	public BoardListVO getAllBoardList();

	public int getCountBoard();

	public void boardRegister(BoardVO bvo, ProductSetVO psvo);

	public BoardVO getBoardDetailByBno(int bno);

	List<ProductVO> getProductImgByBno(int bno);

	void registerApplication(ApplicationVO avo);

	void registerTransaction(TransactionVO tvo);

	public BoardListVO getMyBoardList(String pageNo, String id);

	String isGiveMeChecked(ApplicationVO avo);
}
