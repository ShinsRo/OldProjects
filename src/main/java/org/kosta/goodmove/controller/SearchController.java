package org.kosta.goodmove.controller;

import javax.annotation.Resource;

import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {

	@Resource
	private SearchService searchService;

	@RequestMapping("search.do")
	public String search(SearchVO vo, String pageNo, Model model) {
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
			model.addAttribute("search", searchService.searchBoard(vo, pageNo));
		} else if (vo.getMcategory().equals("comment")) {
			model.addAttribute("search", searchService.searchComment(vo, pageNo));
		}
		model.addAttribute("svo", vo);
		return "search/searchResult.tiles";
	}
}
