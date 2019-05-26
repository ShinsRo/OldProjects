package org.kosta.goodmove.model.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.BoardDAO;
import org.kosta.goodmove.model.dao.CommentDAO;
import org.kosta.goodmove.model.dao.SearchDAO;
import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardPagingBean;
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

	/**
	 * 지역후기 검색
	 */
	@Override
	public CommentListVO searchComment(SearchVO svo, String pageNo) {
		int totalCount = commentDAO.getSearchContentCount(svo);
		PagingBean pagingBean = new PagingBean(totalCount, Integer.parseInt(pageNo));
		return new CommentListVO(searchDAO.searchComment(svo, pagingBean), pagingBean);
	}

	/**
	 * 기부목록 검색
	 */
	@Override
	public BoardListVO searchBoard(SearchVO svo, String pageNo) {
		int totalCount = boardDAO.getSearchContentCount(svo);
		BoardPagingBean pagingBean = new BoardPagingBean(totalCount, Integer.parseInt(pageNo));
		return new BoardListVO(searchDAO.searchBoard(svo, pagingBean), pagingBean);
	}

	/**
	 * 검색조건에 해당하는 글 갯수 반환
	 */
	@Override
	public int count(SearchVO searchVO) {
		int count = 0;
		if (searchVO.getMcategory().equals("board")) {
			count = boardDAO.getSearchContentCount(searchVO);
		} else {
			count = commentDAO.getSearchContentCount(searchVO);
		}
		return count;
	}

	/**
	 * 방문자수 반환
	 */
	@Override
	public int countday(String id, String info) {
		String info2 = null;
		int i = info.lastIndexOf("/");
		info2 = id+info.substring(i);
		return searchDAO.countday(info2);
	}

	/**
	 * 검색 자동완성
	 */
	@Override
	public List<String> getAutoSearchList(String keyword) {
		return searchDAO.getAutoSearchList(keyword);
	}

	@Override
	public HashMap<String, Object> search(SearchVO vo, String pageNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (pageNo == null) {
			pageNo = "1";
		}
		if (vo.getScategory() == null) {
			vo.setScategory("title");
		}
		if (vo.getMcategory() == null) {
			vo.setMcategory("comment");
		}
		if (vo.getMcategory().equals("board")) {
			map.put("search", searchBoard(vo, pageNo));
		} else if (vo.getMcategory().equals("comment")) {
			map.put("search", searchComment(vo, pageNo));
		}
		map.put("btitle", count(new SearchVO("board", "title", vo.getKeyword())));
		map.put("baddr", count(new SearchVO("board", "addr", vo.getKeyword())));
		map.put("bid", count(new SearchVO("board", "id", vo.getKeyword())));
		map.put("ctitle", count(new SearchVO("comment", "title", vo.getKeyword())));
		map.put("caddr", count(new SearchVO("comment", "addr", vo.getKeyword())));
		map.put("cid", count(new SearchVO("comment", "id", vo.getKeyword())));
		map.put("svo", vo);
		String type;
		if (vo.getMcategory().equals("board")) {
			type = "b" + vo.getScategory();
		} else {
			type = "c" + vo.getScategory();
		}
		map.put("type", type);
		map.put("mcategory", vo.getMcategory());
		return map;
	}
}
