package org.kosta.goodmove.model.service;

import java.util.List;

import org.kosta.goodmove.model.vo.CommentListVO;
import org.kosta.goodmove.model.vo.SearchVO;

public interface SearchService {

	List<Object> searchBoard(SearchVO svo, String pageNo);

	CommentListVO searchComment(SearchVO svo, String pageNo);

	int count(SearchVO searchVO);

}
