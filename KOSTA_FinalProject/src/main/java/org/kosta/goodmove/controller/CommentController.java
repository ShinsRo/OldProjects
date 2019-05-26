package org.kosta.goodmove.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.SearchService;
import org.kosta.goodmove.model.vo.CommentPictureVO;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.SearchVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
/**
 * 후기 정보 관할 컨트롤러 : Controller
 * @author AreadyDoneTeam
 * @version 1
 */
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController {

	@Resource
	private CommentService commentService;
	@Resource
	private SearchService searchService;

	/**
	 * 지역후기 리스트를 받아오는 메서드
	 * 
	 * @param 페이지
	 *            번호
	 * @param model
	 * @return 이동될 화면의 경로
	 */
	@RequestMapping("getCommentList.do")
	public String getCommentList(String pageNo, String id, Model model) {
		if (id != null) {
			SearchVO svo = new SearchVO("comment", "id", id);
			pageNo = "1";
			model.addAttribute("lvo", searchService.searchComment(svo, pageNo));
		} else {
			model.addAttribute("lvo", commentService.getCommentList(pageNo));
		}
		return "comment/commentList.tiles";
	}
	/**
	 * 지역후기의 상세내용
	 * 
	 * @param 글번호
	 * @return 이동될 화면의 경로, 검색된 결과 vo
	 */
	@RequestMapping("showComment.do")
	public String showComment(String cno, Model model) {
		int clno = Integer.parseInt(cno);
		model.addAttribute("cvo", commentService.showComment(clno));
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(clno));
		model.addAttribute("likeCount", commentService.getCountLikeByCno(cno));
		model.addAttribute("commentCount", commentService.getCommentReplyCount(cno));
//		return "comment/commentDetail.tiles";
		return "comment/commentDetail_blogVer.tiles";
		}

	/**
	 * 지역후기 수정 화면으로 이동
	 * 
	 * @param cno
	 * @return 이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */
	@RequestMapping("commentUpdateView.do")
	public ModelAndView commentUpdate(String cno) {
		int clno = Integer.parseInt(cno);
		CommentVO cvo = commentService.showCommentNoHit(clno);
		String content = cvo.getContent().replaceAll("\"", "\'");
		cvo.setContent(content);
		return new ModelAndView("comment/commentUpdate.tiles", "cvo", cvo);
	}

	/**
	 * 지역후기 수정
	 * 
	 * @param CommentVO
	 * @return 이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */
	@RequestMapping("commentUpdate.do")
	public String commentUpdate(CommentVO cvo, Model model) {
		commentService.updateBoard(cvo);
		int clno = Integer.parseInt(cvo.getCno());
		model.addAttribute("cvo", commentService.showCommentNoHit(clno));
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(clno));
		return "comment/commentDetail_blogVer.tiles";
	}

	/**
	 * 지역후기 등록 페이지로 이동
	 * 
	 * @return 이동될 화면의 경로
	 */
	@RequestMapping("commentRegisterView.do")
	public String commentRehisterView(HttpServletRequest request, Model model) {
		MemberVO mvo = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String add = mvo.getAddr();
		if (add.lastIndexOf("길") > 0) {
			model.addAttribute("add", add.substring(0, add.lastIndexOf("길") + 1));
		} else if (add.lastIndexOf("로") < 0 && add.lastIndexOf("길") > 0) {
			model.addAttribute("add", add.substring(0, add.lastIndexOf("로") + 1));
		} else {
			model.addAttribute("add", add);
		}
		return "comment/commentRegister.tiles";
	}

	/**
	 * 지역후기 등록
	 * 
	 * @param 로그인
	 *            정보를 받아오기 위해 받아온 request
	 * @param 사용자에
	 *            의해 작성된 Comment 내용
	 * @return 이동될 화면의 경로, 새로고침 적용되지 않게함, 조회수를 증가하지 않고 검색 시도
	 */
	@RequestMapping(value = "commentRegister.do", method = RequestMethod.POST)
	public ModelAndView write(String addr, HttpServletRequest request, CommentVO cvo) {
		MemberVO mvo = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		cvo.setId(mvo.getId());
		cvo.setAddr(addr);
		commentService.commentRegister(cvo);
		return new ModelAndView("redirect:showCommentNoHit.do?cno=" + cvo.getCno());
	}

	/**
	 * 조회수를 증가하지 않고 Comment조회
	 * 
	 * @param 글번호
	 * @return 이동될 화면의 경로, 조회수를 증가하지 않고 받아온 검색결과vo
	 */

	@RequestMapping("showCommentNoHit.do")
	public String showCommentNoHit(String cno, Model model) {
		int clno = Integer.parseInt(cno);
		model.addAttribute("CommentReplyList", commentService.getAllCommentReplyList(clno));
		model.addAttribute("cvo", commentService.showCommentNoHit(Integer.parseInt(cno)));
		model.addAttribute("likeCount", commentService.getCountLikeByCno(cno));
		model.addAttribute("commentCount", commentService.getCommentReplyCount(cno));
		return "comment/commentDetail_blogVer.tiles";
	}

	/**
	 * 지역후기 삭제
	 * 
	 * @param cno
	 * @return
	 */
	@RequestMapping("deleteComment.do")
	public ModelAndView deleteBoard(int cno) {
		commentService.deleteComment(cno);
		return new ModelAndView("comment/commentList.tiles", "lvo", commentService.getCommentList());
	}

	/**
	 * 댓글
	 * 
	 * @param crvo
	 * @return
	 */
	@RequestMapping("writeCommentReply1.do")
	public String writeCommentReply1(CommentReplyVO crvo) {
		int rno = commentService.getNextReplyNo();
		int gno = rno;
		commentService.insertNewCommentReply(new CommentReplyVO(rno, crvo.getCno(), crvo.getId(), crvo.getName(),
				crvo.getParent(), crvo.getContent(), gno, crvo.getdepth(), crvo.getOrder_no()));
		return "redirect:showCommentNoHit.do?cno=" + crvo.getCno();
	}

	/**
	 * 대댓글
	 * 
	 * @param crvo
	 * @return
	 */
	@RequestMapping("writeCommentReply2.do")
	public String writeCommentReply(CommentReplyVO crvo) {
		int rno = commentService.getNextReplyNo();
		crvo.setGno(crvo.getParent());
		CommentReplyVO pvo = commentService.getParentInfo(crvo.getParent());
		if (commentService.getParentsParentId(crvo.getParent()) != 0) {
			crvo.setParent(commentService.getParentsParentId(crvo.getParent()));
			crvo.setGno(crvo.getParent());
		}
		commentService.insertNewCommentReply(new CommentReplyVO(rno, crvo.getCno(), crvo.getId(), crvo.getName(),
				crvo.getParent(), crvo.getContent(), crvo.getGno(), pvo.getdepth(), pvo.getOrder_no()));
		return "redirect:showCommentNoHit.do?cno=" + crvo.getCno();
	}

	/**
	 * 댓글삭제
	 * 
	 * @param rno
	 * @param cno
	 * @return
	 */
	@RequestMapping("deleteCommentReply.do")
	public String deleteCommentReply(int rno, int cno) {
		CommentReplyVO crvo = commentService.getCommentReplyInfoByRNO(rno);
		commentService.deleteCommentReply(rno);
		if (crvo.getParent() == 0)
			commentService.deleteCommentReplyChild(crvo.getGno());
		return "redirect:showCommentNoHit.do?cno=" + cno;
	}

	/**
	 * 댓글 수정
	 * 
	 * @param cno
	 * @param rno
	 * @param content
	 * @return
	 */
	@RequestMapping("updateCommentReply.do")
	public String updateCommentReply(int cno, int rno, String content) {
		CommentReplyVO crvo = new CommentReplyVO(rno, content);
		commentService.updateCommentReply(crvo);
		return "redirect:showCommentNoHit.do?&cno=" + cno;
	}

	/**
	 * 아이디 이용하여 내가작성한 지역후기 리스트 반환
	 * 
	 * @param id
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("findCommentListById.do")
	public String findCommentById(String id, String pageNo, Model model) {
		model.addAttribute("lvo", commentService.findCommentListById(id, pageNo));
		return "comment/commentList.tiles";
	}
	
	@RequestMapping("getPicNo.do")
	@ResponseBody
	public String getPicNo(){
		return commentService.getPicNo();
	}
	
	@RequestMapping("stackImg.do")
	public String stackImg(HttpServletRequest req,  CommentPictureVO cpvo, MultipartFile file){
		MemberVO mvo = (MemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = mvo.getId();
		String uploadPath = "";
		 uploadPath =  req.getSession().getServletContext().getRealPath("/uploadedFiles/");
		 uploadPath += userId + "\\" + "comment" + cpvo.getPicno() + "\\";
				// 로컬 깃 레퍼지토리 경로
/*				uploadPath = "C:\\Users\\KOSTA\\git\\GoodMoveRepository\\src\\main\\webapp\\uploadedFiles\\" + userId + "\\"
						+ "comment" + cpvo.getPicno() + "\\";*/
					String fileName = file.getOriginalFilename();
					String fileSuffix = fileName.substring(fileName.lastIndexOf('.'));

					// 물건 리스트 초기화
					String img_path = "uploadedFiles\\" +userId + "\\"
							+ "comment" + cpvo.getPicno() + "\\"+ cpvo.getPic_cursor() + fileSuffix;
					cpvo.setImg_path(img_path);
					
					if (fileName.equals("") == false) {
						try {
							new File(uploadPath).mkdirs();
							file.transferTo(new File(uploadPath + cpvo.getPic_cursor() + fileSuffix));
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					commentService.stackImg(cpvo);
		return "comment/imgSelectResult";
	}
	@RequestMapping("getImgPath.do")
	@ResponseBody
	public Object getImgPath(CommentPictureVO cpvo){
		String resultImg = commentService.getImgPath(cpvo);
		return resultImg;
	}
	
	@RequestMapping("showImgSelector.do")
	public String showImgSelector(){
		return "comment/imgSelectPopUp";
	}
	/**
	 * 좋아요 버튼 클릭시
	 * 테이블에 insert
	 * @return
	 */
	@RequestMapping("clickLikeBtn.do")
	@ResponseBody
	public Map<String, String> clickLikeBtn(String cno,String id){
		int count = commentService.findLikeById(cno, id);
		Map<String, String> result = new HashMap<>();
		if(count==0){ // 좋아요 안눌렀으면
			commentService.clickLikeBtn(cno, id);// db insert
			result.put("count", Integer.toString(commentService.getCountLikeByCno(cno)));
			result.put("status", "ok");
		}else{ // 좋아요 이미 누름
			commentService.unclickLikeBtn(cno, id);
			result.put("count", Integer.toString(commentService.getCountLikeByCno(cno)));
			result.put("status", "fail");
		}
		return result;
	}
	@RequestMapping("checkClickLike.do")
	@ResponseBody
	public String checkClickLike(String cno,String id){
		int count = commentService.findLikeById(cno, id);
		System.out.println(count);
		return (count == 0) ? "ok" : "fail";
	}
}
