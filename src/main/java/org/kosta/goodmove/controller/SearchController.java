package org.kosta.goodmove.controller;

import javax.annotation.Resource;

import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.MemberService;
import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

	@Resource
	private SearchService searchService;
	@Resource
	private BoardService boardService;
	@Resource
	private CommentService commentService;
	@Resource
	private MemberService memberService;
	
	@RequestMapping("getCountBoardMain.do")
	@ResponseBody
	public int getCountBoardMain(){
		return boardService.getCountBoard();
	}
	
	@RequestMapping("getCountCommentMain.do")
	@ResponseBody
	public int getCountCommentMain(){
		return commentService.getTotalContentCount();
	}
	@RequestMapping("getCountMemberMain.do")
	@ResponseBody
	public int getCountMemberMain(){
		return memberService.getMemberCount();
	}
	
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
		model.addAttribute("btitle", searchService.count(new SearchVO("board", "title", vo.getWord())));
		model.addAttribute("baddr", searchService.count(new SearchVO("board", "addr", vo.getWord())));
		model.addAttribute("bid", searchService.count(new SearchVO("board", "id", vo.getWord())));
		model.addAttribute("ctitle", searchService.count(new SearchVO("comment", "title", vo.getWord())));
		model.addAttribute("caddr", searchService.count(new SearchVO("comment", "addr", vo.getWord())));
		model.addAttribute("cid", searchService.count(new SearchVO("comment", "id", vo.getWord())));
		model.addAttribute("svo", vo);
		String type;
		if(vo.getMcategory().equals("board")){
			type="b"+vo.getScategory();
		}else{
			type="c"+vo.getScategory();
		}
		model.addAttribute("type", type);
		return "search/searchResult.tiles";
	}
}

