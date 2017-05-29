package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.BoardDAO;
import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardPagingBean;
import org.kosta.goodmove.model.vo.BoardVO;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{
	@Resource
	private BoardDAO boardDAO;

	@Override
	public int getNextBno() {
		return boardDAO.getNextBno();
	}

	@Override
	public BoardListVO getAllBoardList(){
		return getAllBoardList("1");
	}
	@Override
	public BoardListVO getAllBoardList(String pageNo) {
		int totalCount=boardDAO.getTotalBoardCount();
		BoardPagingBean pagingBean=null;
		if(pageNo==null)
			pagingBean= new BoardPagingBean(totalCount);
		else
			pagingBean= new BoardPagingBean(totalCount,Integer.parseInt(pageNo));		
		return new BoardListVO(boardDAO.getAllBoardList(pagingBean),pagingBean);
	}
	@Override
	public BoardVO getBoardDetailByBno(int bno){
		return boardDAO.getBoardDetailByBno(bno);
	}

}
