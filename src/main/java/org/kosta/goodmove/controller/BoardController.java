package org.kosta.goodmove.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.vo.BoardListVO;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.ProductSetVO;
import org.kosta.goodmove.model.vo.ProductVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
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
		BoardListVO blvo = boardService.getAllBoardList(pageNo);
		model.addAttribute("blvo", blvo);
		return "board/boardList.tiles";
	}
	/**
	 * 리스트에서 상세보기 눌렀을 때
	 * 번호에 따라 bvo 반환
	 * @param bno
	 * @param model
	 * @return
	 */
	@RequestMapping("boardDetail.do")
	public String boardDetail(String bno,Model model){
		BoardVO bvo = boardService.getBoardDetailByBno(Integer.parseInt(bno));
		List<ProductVO> plist = boardService.getProductImgByBno(Integer.parseInt(bno));
		model.addAttribute("bvo", bvo);
		model.addAttribute("plist", plist);
		return "board/boardDetail.tiles";
	}
	
	@RequestMapping("boardRegisterView.do")
	public String boardRegisterView(){
		return "board/boardRegister.tiles";
	}
	
	@RequestMapping("boardRegister.do")
	public String fileUpload(HttpServletRequest req,  BoardVO bvo, ProductSetVO psvo){
		
		int bno = boardService.getNextBno();
		String userId = ((MemberVO)req.getSession(false).getAttribute("mvo")).getId();
		
		bvo.setId(userId);
		bvo.setBno(bno);
		
		//실제 운영시에 사용할 서버 업로드 경로
		//uploadPath = req.getSession().getServletContext().getRealPath("/resources/upload/");
		
		//로컬 깃 레퍼지토리 경로
		uploadPath = "C:\\Users\\KOSTA\\git\\GoodMoveRepository\\src\\main\\webapp\\uploadedFiles\\"
				+userId+"\\"+"board"+bno+"\\";
		
		bvo.setpList(new ArrayList<ProductVO>());
		
		List<MultipartFile> list = psvo.getFile();
		for(int i = 0; i < list.size(); i ++){
			String fileName = list.get(i).getOriginalFilename();
			String fileSuffix = fileName.substring(fileName.lastIndexOf('.'));
			/*System.out.println(fileSuffix);*/
			//물건 번호 초기화
			int nPno = boardService.getNextPno();
			
			//물건 리스트 초기화
			ProductVO tempPVO = new ProductVO();
			tempPVO.setPno(nPno);
			tempPVO.setImg_path("uploadedFiles\\"+userId+"\\"+"board"+bno+"\\"+nPno+fileSuffix);
			bvo.getpList().add(tempPVO);
			
			if(fileName.equals("")==false){
				try {
					new File(uploadPath).mkdirs();
					list.get(i).transferTo(new File(uploadPath+nPno+fileSuffix));
					/*System.out.println("업로드 완료 "+fileName);*/
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//Board Thumb nail 저장
		bvo.setThumbPath(bvo.getpList().get(0).getImg_path());
		
		boardService.boardRegister(bvo, psvo);
		return "home.tiles";
	}
}
