package org.kosta.goodmove.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.MemberService;
import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
	 */
	@RequestMapping("getCountToDay.do")
	@ResponseBody
	public int getCountToDay(HttpServletRequest request) {
		MemberVO mvo = (MemberVO) request.getSession().getAttribute("mvo");
		String info = null;
		if (mvo != null) {
			try {
				int i = InetAddress.getLocalHost().toString().lastIndexOf("/");
				info = mvo.getId() + InetAddress.getLocalHost().toString().substring(i);

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				int i = InetAddress.getLocalHost().toString().lastIndexOf("/");
				info = InetAddress.getLocalHost().toString().substring(i);
				;

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return searchService.countday(info);
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
		if (vo.getMcategory().equals("board")) {
			type = "b" + vo.getScategory();
		} else {
			type = "c" + vo.getScategory();
		}
		model.addAttribute("type", type);
		model.addAttribute("mcategory", vo.getMcategory());
		return "search/searchResult.tiles";
	}

	@RequestMapping("autoSearch.do")
	@ResponseBody
	public void autoSearch(ModelMap modelMap, String keyword, HttpServletResponse response)
			throws IOException {
		List<String> searchList = null;
		System.out.println(keyword);
		searchList = searchService.getAutoSearchList(keyword);
		System.out.println(searchList);
		PrintWriter out = response.getWriter();
		out.print(searchList.toString());
	}
}
