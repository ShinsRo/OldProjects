package org.kosta.goodmove.controller;

import javax.annotation.Resource;

import org.kosta.goodmove.model.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 게시 정보 관할 컨트롤러 : Controller
 * @author AreadyDoneTeam
 * @version 1
 */
@Controller
public class BoardController {
	@Resource
	private BoardService boardService;
	
	@RequestMapping("getBoardList.do")
	public String boardList(String pageNo, Model model){
		model.addAttribute("blvo", boardService.getAllBoardList(pageNo));
		return "board/boardList.tiles";
	}
	@RequestMapping("boardDetail.do")
	public String boardDetail(String bno,Model model){
		model.addAttribute("bno", bno);
		return "board/boardDetail.tiles";
	}
}
