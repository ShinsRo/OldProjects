package org.kosta.goodmove.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductSetVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
/**
 * 게시 정보 관할 컨트롤러 : Controller
 * @author AreadyDoneTeam
 * @version 1
 */
@Controller
public class BoardController {
	@Resource
	private BoardService boardService;
	private String uploadPath;
	
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
	
	@RequestMapping("boardRegister.do")
	public ModelAndView fileUpload(HttpServletRequest req, BoardVO bvo, ProductSetVO psvo){
		//실제 운영시에 사용할 서버 업로드 경로
		uploadPath = req.getSession().getServletContext().getRealPath("/resources/upload/");
		//개발 시에는 워크스페이스 업로드 경로로 준다.
		//uploadPath = "C:\\Java-kosta\\framework-workspace2\\springmvc21-fileupload\\src\\main\\webapp\\resources\\upload\\";
		List<MultipartFile> list = psvo.getFile();
		//결과 응답 화면에 파일명 목록을 전달하기 위한 리스트
		ArrayList<String> nameList = new ArrayList<String>();
		for(int i = 0; i < list.size(); i ++){
			String fileName = list.get(i).getOriginalFilename();
			if(fileName.equals("")==false){
				try {
					list.get(i).transferTo(new File(uploadPath+fileName));
					nameList.add(fileName);
					System.out.println("업로드 완료 "+fileName);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("nameList", nameList);
		mv.setViewName("product/multifileupload_result.tiles");
		return mv;
	}
}
