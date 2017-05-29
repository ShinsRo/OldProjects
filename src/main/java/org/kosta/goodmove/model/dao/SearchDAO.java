package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.kosta.goodmove.model.vo.SearchVO;

public interface SearchDAO {

	List<CommentVO> searchComment(SearchVO svo, PagingBean pagingBean);

}
