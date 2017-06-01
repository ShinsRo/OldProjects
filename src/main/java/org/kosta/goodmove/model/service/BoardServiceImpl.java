package org.kosta.goodmove.model.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.BoardDAO;
import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardPagingBean;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductSetVO;
import org.kosta.goodmove.model.vo.ProductVO;
import org.kosta.goodmove.model.vo.TransactionVO;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
	@Resource
	private BoardDAO boardDAO;

	@Override
	public int getNextBno() {
		return boardDAO.getNextBno();
	}

	@Override
	public int getNextPno() {
		return boardDAO.getNextPno();
	}

	@Override
	public int getCountBoard() {
		return boardDAO.getTotalBoardCount();
	}
	@Override
	public BoardListVO getAllBoardList() {
		return getAllBoardList("1");
	}

	@Override
	public BoardListVO getAllBoardList(String pageNo) {
		int totalCount = boardDAO.getTotalBoardCount();
		BoardPagingBean pagingBean = null;
		if (pageNo == null)
			pagingBean = new BoardPagingBean(totalCount);
		else
			pagingBean = new BoardPagingBean(totalCount, Integer.parseInt(pageNo));
		BoardListVO bList = new BoardListVO(boardDAO.getAllBoardList(pagingBean), pagingBean);
		for (Iterator<BoardVO> it = bList.getList().iterator(); it.hasNext();) {
			BoardVO bvo = it.next();
			StringTokenizer tempAddr = new StringTokenizer(bvo.getAddr(), " ");
			bvo.setAddr(tempAddr.nextToken() + " " + tempAddr.nextToken());
		}
		return bList;
	}

	@Override
	public BoardListVO getMyBoardList(String pageNo, String id) {
		int totalCount = boardDAO.getTotalBoardCount(id);
		BoardPagingBean pagingBean = null;
		if (pageNo == null)
			pagingBean = new BoardPagingBean(totalCount);
		else
			pagingBean = new BoardPagingBean(totalCount, Integer.parseInt(pageNo));
		
		BoardListVO bList = new BoardListVO(boardDAO.getMyBoardList(pagingBean, id), pagingBean);
		
		for (Iterator<BoardVO> it = bList.getList().iterator(); it.hasNext();) {
			BoardVO bvo = it.next();
			bvo.setaList(boardDAO.getApplications(bvo.getBno()));
			StringTokenizer tempAddr = new StringTokenizer(bvo.getAddr(), " ");
			bvo.setAddr(tempAddr.nextToken() + " " + tempAddr.nextToken());
		}
		return bList;
	}

	@Override
	public BoardVO getBoardDetailByBno(int bno) {
		BoardVO bvo = boardDAO.getBoardDetailByBno(bno);
		String addr = bvo.getAddr();
		String newAddr = "";
		StringTokenizer str = new StringTokenizer(addr, " ");
		for (int i = 0; i < 2; i++) {
			String data = str.nextToken();
			newAddr += data + " ";
		}
		bvo.setAddr(newAddr);
		return bvo;
	}

	@Override
	public void boardRegister(BoardVO bvo, ProductSetVO psvo) {
		System.out.println(bvo);
		List<ProductVO> pList = bvo.getpList();
		for (int i = 0; i < pList.size(); i++) {
			ProductVO tempPVO = pList.get(i);
			tempPVO.setBno(bvo.getBno());
			tempPVO.setPtitle(psvo.getPtitle().remove(0));
			tempPVO.setKind(psvo.getKind().remove(0));
			tempPVO.setPcontent(psvo.getPcontent().remove(0));
		}
		boardDAO.boardRegister(bvo);
	}
	
	@Override
	public List<ProductVO> getProductImgByBno(int bno){
		return boardDAO.getProductImgByBno(bno);
	}

	@Override
	public int getNextAno() {
		return boardDAO.getNextAno();
	}

	@Override
	public int getNextTno() {
		return boardDAO.getNextTno();
	}

	@Override
	public void registerApplication(ApplicationVO avo) {
		boardDAO.registerApplication(avo);
	}

	@Override
	public void registerTransaction(TransactionVO tvo) {
		boardDAO.registerTransaction(tvo);
	}
}
