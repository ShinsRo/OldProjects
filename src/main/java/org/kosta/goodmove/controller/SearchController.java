package org.kosta.goodmove.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.MemberService;
import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	/**
	 * 메인화면에 표시되는 기부물품 글갯수
	 * 
	 * @return
	 */
	@RequestMapping("getCountBoardMain.do")
	@ResponseBody
	public int getCountBoardMain() {
		return boardService.getCountBoard();
	}

	/**
	 * 메인화면에 표시되는 지역후기 글갯수
	 * 
	 * @return
	 */
	@RequestMapping("getCountCommentMain.do")
	@ResponseBody
	public int getCountCommentMain() {
		return commentService.getTotalContentCount();
	}

	/**
	 * 메인화면에 표시되는 회원수
	 * 
	 * @return
	 */
	@RequestMapping("getCountMemberMain.do")
	@ResponseBody
	public int getCountMemberMain() {
		return memberService.getMemberCount();
	}

	/**
	 * 메인화면에 표시되는 오늘의 방문자
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping("getCountToDay.do")
	@ResponseBody
	public int getCountToDay(HttpServletRequest request) throws UnknownHostException {
		MemberVO mvo = (MemberVO) request.getSession().getAttribute("mvo");
		return searchService.countday(mvo, InetAddress.getLocalHost().toString());
	}

	/**
	 * 검색기능
	 * 
	 * @param vo
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("search.do")
	public ModelAndView search(SearchVO vo, String pageNo) {
		return new ModelAndView("search/searchResult.tiles", "search", searchService.search(vo, pageNo));
	}

	/**
	 * 검색어 자동완성
	 * @param modelMap
	 * @param keyword
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("autoSearch.do")
	@ResponseBody
	public List<String> autoSearch(ModelMap modelMap, String keyword, HttpServletResponse response)
			throws IOException {
		List<String> searchList = null;
		searchList = searchService.getAutoSearchList(keyword);
		PrintWriter out = response.getWriter();
		out.print(searchList.toString());
		System.out.println(searchList);
		return searchList;
	}
}
