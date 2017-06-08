package org.kosta.goodmove.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.BoardPagingBean;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.kosta.goodmove.model.vo.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDAOImpl implements SearchDAO {
	
	@Resource
	 private SqlSessionTemplate template;

	/**
	 * 지역후기 검색
	 */
	@Override
	public List<CommentVO> searchComment(SearchVO svo, PagingBean pagingBean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("scategory", svo.getScategory());
		map.put("word", svo.getWord());
		map.put("startRowNumber", pagingBean.getStartRowNumber());
		map.put("endRowNumber", pagingBean.getEndRowNumber());
		return template.selectList("comment.search", map);
	}

	/**
	 * 기부글 검색
	 */
	@Override
	public List<BoardVO> searchBoard(SearchVO svo, BoardPagingBean pagingBean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("scategory", svo.getScategory());
		map.put("word", svo.getWord());
		map.put("startRowNumber", pagingBean.getStartRowNumber());
		map.put("endRowNumber", pagingBean.getEndRowNumber());
		return template.selectList("board.search", map);
	}
	
	/**
	 * 당일 방문자 증가, 반환
	 */
	@Override 
	public int countday(String info) {
		String today = template.selectOne("member.today");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("today", today);
		map.put("info", info);
		int findtoday = template.selectOne("member.findtoday", map);
		if(findtoday==0){
			template.insert("member.insertday", map);
			template.update("member.upcount", map);
		}else{
			template.update("member.upcount", map);
		}
		return template.selectOne("member.getcount", today);
	}

	/**
	 * 검색거 자동추천
	 */
	@Override 
	public List<String> getAutoSearchList(String keyword) {
		return template.selectList("comment.getAutoSearchList",keyword);
	}
}
