package org.kosta.goodmove.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.BoardDAO;
import org.kosta.goodmove.model.dao.CommentDAO;
import org.kosta.goodmove.model.dao.SearchDAO;
import org.kosta.goodmove.model.vo.CommentListVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.kosta.goodmove.model.vo.SearchVO;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

	@Resource
	private SearchDAO searchDAO;
	
	@Resource
	private CommentDAO commentDAO;
	
	@Resource
	private BoardDAO boardDAO;
	
	@Override
	public CommentListVO searchComment(SearchVO svo, String pageNo) {
		int totalCount = commentDAO.getSearchContentCount(svo);
		System.out.println("토탈카운트" + totalCount);
		PagingBean pagingBean=new PagingBean(totalCount,Integer.parseInt(pageNo));
		return new CommentListVO(searchDAO.searchComment(svo, pagingBean),pagingBean);
	}
	

	@Override
	public List<Object> searchBoard(SearchVO svo, String pageNo) {
		//int totalCount=boardDAO.getTotalContentCount();
		//pagingBean=new PagingBean(totalCount,Integer.parseInt(pageNo));
		//return new BoardListVO(searchDAO.search(svo, pagingBean),pagingBean);
		return null;
	}


	@Override
	public int count(SearchVO searchVO) {
		int count = 0;
		if(searchVO.getMcategory().equals("board")){
			//count = boardDAO.getSearchContentCount(searchVO);
		}else{
			count = commentDAO.getSearchContentCount(searchVO);
		}
		return count;
	}

}
