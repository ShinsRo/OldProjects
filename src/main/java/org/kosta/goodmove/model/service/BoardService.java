package org.kosta.goodmove.model.service;

import java.util.List;
import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductSetVO;
import org.kosta.goodmove.model.vo.ProductVO;
import org.kosta.goodmove.model.vo.TransactionVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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

	public BoardVO getBoardDetailByBnoWithFullAddr(int bno);

	List<ProductVO> getProductImgByBno(int bno);

	void registerApplication(ApplicationVO avo);

	void registerTransaction(TransactionVO tvo);

	public BoardListVO getMyBoardList(String pageNo, String id);

	String isGiveMeChecked(ApplicationVO avo);

	public List<ApplicationVO> getApplications(int bno_int);

	List<ApplicationVO> getApplicationsById(String id);

	public void confirmApply(String bno, String id);

	public void delete(String bno);

	public String getProductURL(String pno);

	public int getProductCnt(int bno);

	public void boardUpdate(BoardVO bvo, ProductSetVO psvo, int newProductCnt, String[] deletedProductArr);

	public BoardListVO boardListById(String id, String pageNo);
	
	void updateBoardHit(int bno);
}
