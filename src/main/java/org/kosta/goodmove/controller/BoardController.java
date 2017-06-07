package org.kosta.goodmove.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.vo.ApplicationVO;
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
 * 
 * @author AreadyDoneTeam
 * @version 1
 */
@Controller
public class BoardController {
	@Resource
	private BoardService boardService;
	private String uploadPath;

	/**
	 * 드려요 리스트 페이지로 이동
	 * 
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("getBoardList.do")
	public String boardList(String pageNo, Model model) {
		BoardListVO blvo = boardService.getAllBoardList(pageNo);
		model.addAttribute("blvo", blvo);
		return "board/boardList.tiles";
	}

	/**
	 * 내가 작성한 드려요 리스트
	 * 
	 * @param pageNo
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("myBoardList.do")
	public String myBoardList(String pageNo, Model model, HttpServletRequest req) {
		String id = ((MemberVO) req.getSession(false).getAttribute("mvo")).getId();
		BoardListVO blvo = boardService.getMyBoardList(pageNo, id);
		model.addAttribute("blvo", blvo);
		return "mypage/my_board.tiles";
	}

	/**
	 * 리스트에서 상세보기 눌렀을 때 번호에 따라 bvo 반환
	 * 
	 * @param bno
	 * @param model
	 * @return
	 */
	@RequestMapping("boardDetail.do")
	public String boardDetail(String bno, Model model) {
		BoardVO bvo = boardService.getBoardDetailByBno(Integer.parseInt(bno));
		List<ProductVO> plist = boardService.getProductImgByBno(Integer.parseInt(bno));
		model.addAttribute("bvo", bvo);
		model.addAttribute("plist", plist);
		return "board/boardDetail.tiles";
	}

	/**
	 * 드려요 작성 페이지 이동
	 * 
	 * @return
	 */
	@RequestMapping("boardRegisterView.do")
	public String boardRegisterView() {
		return "board/boardRegister.tiles";
	}

	/**
	 * 드려요 작성 컨트롤러
	 * 
	 * @param req
	 * @param bvo
	 * @param psvo
	 * @return
	 */
	@RequestMapping("boardRegister.do")
	public String fileUpload(HttpServletRequest req, BoardVO bvo, ProductSetVO psvo) {

		int bno = boardService.getNextBno();
		String userId = ((MemberVO) req.getSession(false).getAttribute("mvo")).getId();

		bvo.setId(userId);
		bvo.setBno(bno);

		// 실제 운영시에 사용할 서버 업로드 경로
		// uploadPath =
		// req.getSession().getServletContext().getRealPath("/resources/upload/");

		// 로컬 깃 레퍼지토리 경로
		uploadPath = "C:\\Users\\KOSTA\\git\\GoodMoveRepository\\src\\main\\webapp\\uploadedFiles\\" + userId + "\\"
				+ "board" + bno + "\\";

		bvo.setpList(new ArrayList<ProductVO>());

		List<MultipartFile> list = psvo.getFile();
		for (int i = 0; i < list.size(); i++) {
			String fileName = list.get(i).getOriginalFilename();
			String fileSuffix = fileName.substring(fileName.lastIndexOf('.'));
			// 물건 번호 초기화
			int nPno = boardService.getNextPno();

			// 물건 리스트 초기화
			ProductVO tempPVO = new ProductVO();
			tempPVO.setPno(nPno);
			tempPVO.setImg_path("uploadedFiles\\" + userId + "\\" + "board" + bno + "\\" + nPno + fileSuffix);
			bvo.getpList().add(tempPVO);

			if (fileName.equals("") == false) {
				try {
					new File(uploadPath).mkdirs();
					list.get(i).transferTo(new File(uploadPath + nPno + fileSuffix));
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// Board Thumb nail 저장
		bvo.setThumbPath(bvo.getpList().get(0).getImg_path());

		boardService.boardRegister(bvo, psvo);
		return "redirect:boardDetail.do?bno=" + bvo.getBno();
	}

	/**
	 * 주세요 신청할 시 transacion,application table에 db 저장
	 * 
	 * @return
	 */
	@RequestMapping("registerGiveMe.do")
	public String registerGiveMe(ApplicationVO avo, HttpServletRequest req, String writer, int bno) {
		String userId = ((MemberVO) req.getSession(false).getAttribute("mvo")).getId();
		// application
		avo.setId(userId);
		avo.setBno(bno);
		if (boardService.isGiveMeChecked(avo).equals("ok")) {
			boardService.registerApplication(avo);
			return "redirect:boardDetail.do?bno=" + bno;
		} else {
			return "board/giveMe_fail.tiles";
		}

	}

	/**
	 * 내가 받은 주세요 신청들 보여주기
	 * 
	 * @param bno
	 * @param model
	 * @return
	 */
	@RequestMapping("getApplications.do")
	public String getApplication(String bno, Model model) {
		int bno_int = Integer.parseInt(bno);
		List<ApplicationVO> aList = boardService.getApplications(bno_int);
		model.addAttribute("aList", aList);
		return "mypage/applications";
	}

	/**
	 * 주세요 신청 채택
	 * 
	 * @param bno
	 * @param id
	 * @return
	 */
	@RequestMapping("confirmApply.do")
	public String confirmApply(String bno, String id) {
		boardService.confirmApply(bno, id);
		return "redirect:getApplications.do?bno=" + bno;
	}
	@RequestMapping("boardDelete.do")
	public String boardDelete(String bno){
		boardService.delete(bno);
		return "redirect:board/boardDelete";
	}
	/**
	 * 아이디로 검색한 주세요 신청 반환
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("getApplicationsById.do")
	public String getApplicationsById(HttpServletRequest req, Model model) {
		String id = ((MemberVO) req.getSession(false).getAttribute("mvo")).getId();
		List<ApplicationVO> appList = boardService.getApplicationsById(id);
		model.addAttribute("appList", appList);
		return "mypage/my_application.tiles";
	}

	@RequestMapping("getBoardList_admin.do")
	public String getBoardList_admin(String pageNo, Model model) {
		return null;
	}
}
