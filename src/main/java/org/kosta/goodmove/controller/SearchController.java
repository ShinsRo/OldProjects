package org.kosta.goodmove.controller;

import javax.annotation.Resource;

import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

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
}
