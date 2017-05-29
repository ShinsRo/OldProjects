package org.kosta.goodmove.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.BoardDAO;
import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardPagingBean;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductSetVO;
import org.kosta.goodmove.model.vo.ProductVO;
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
		return new BoardListVO(boardDAO.getAllBoardList(pagingBean), pagingBean);
	}

	@Override
	public void boardRegister(BoardVO bvo, ProductSetVO psvo) {
		System.out.println(bvo);
		List<ProductVO> pList = bvo.getpList();
		for (int i = 0; i < pList.size(); i++){
			ProductVO tempPVO = pList.get(i);
			tempPVO.setBno(bvo.getBno());
			tempPVO.setPtitle(psvo.getPtitle().remove(0));
			tempPVO.setKind(psvo.getKind().remove(0));
			tempPVO.setPcontent(psvo.getPcontent().remove(0));
		}
		boardDAO.boardRegister(bvo);
	}
}
