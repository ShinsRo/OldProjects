package org.kosta.goodmove.model.service;

import java.util.Iterator;
import java.util.StringTokenizer;

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
		BoardListVO bList = new BoardListVO(boardDAO.getAllBoardList(pagingBean),pagingBean);
		for (Iterator<BoardVO> it = bList.getList().iterator(); it.hasNext();) {
			BoardVO bvo = it.next();
			StringTokenizer tempAddr = new StringTokenizer(bvo.getAddr(), " ");
			bvo.setAddr(tempAddr.nextToken()+" "+tempAddr.nextToken());
		}
		return bList;
	}
	@Override
	public BoardVO getBoardDetailByBno(int bno){
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

}
