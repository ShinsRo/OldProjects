package org.kosta.goodmove.model.service;

import java.util.List;

import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.CommentListVO;
import org.kosta.goodmove.model.vo.SearchVO;

public interface SearchService {

	BoardListVO searchBoard(SearchVO svo, String pageNo);

	CommentListVO searchComment(SearchVO svo, String pageNo);

	int count(SearchVO searchVO);

	int countday(String info);

	List<String> getAutoSearchList(String keyword);

}
