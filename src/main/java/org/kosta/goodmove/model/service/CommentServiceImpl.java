package org.kosta.goodmove.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.CommentDAO;
import org.kosta.goodmove.model.vo.CommentListVO;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
	@Resource
	private CommentDAO commentDAO;
	/**
	 * 페이지 정보가 없이 지역후기 게시판의 리스트 요청 시 사용
	 * @return	현재 페이지번호로 1반환
	 */
	@Override
	public CommentListVO getCommentList() {
		return getCommentList("1");
	}

	/**
	 * 지역후기 게시판 리스트 요청, 반환
	 * @param	현재 페이지 번호
	 * @return	조회된 리스트와 페이징빈
	 */
	@Override
	public CommentListVO getCommentList(String pageNo) {
		int totalCount=commentDAO.getTotalContentCount();
		PagingBean pagingBean=null;
		if(pageNo==null)
			pagingBean=new PagingBean(totalCount);
		else
			pagingBean=new PagingBean(totalCount,Integer.parseInt(pageNo));		
		/*HashMap<String,Integer> paramMap=new HashMap<String,Integer>();
		paramMap.put("startRowNumber",pagingBean.getStartRowNumber());
		paramMap.put("endRowNumber", pagingBean.getEndRowNumber());*/
		return new CommentListVO(commentDAO.getCommentList(pagingBean),pagingBean);
	}
	
	/**
	 * 지역후기 상세내용 검색, 반환
	 * 조회수 증가
	 * @param	글번호
	 * @return	조회된 글 상세내용
	 */
	@Override
	public CommentVO showComment(int clno){
		commentDAO.updateCount(clno);
		return commentDAO.showComment(clno);
	}
	
	/**
	 * hit수 증가
	 */
	@Override
	public void updateCount(int clno){
		commentDAO.updateCount(clno);
	}

	/**
	 * 지역후기 상세내용 검색, 반환
	 * 조회수 증가하지 않음
	 * @param	글번호
	 * @return	조회된 글 상세내용
	 */
	@Override
	public CommentVO showCommentNoHit(int clno) {
		return commentDAO.showComment(clno);
	}

	/**
	 * 지역후기 상세내용 수정
	 * @param	변경된 내용
	 */
	@Override
	public void updateBoard(CommentVO cvo) {
		commentDAO.commentUpdate(cvo);
	}

	/**
	 * 지역후기 등록
	 * @param	고객이 작성한 지역후기 내용
	 */
	@Override
	public void commentRegister(CommentVO cvo) {
		commentDAO.commentRegister(cvo);
	}
	
	/**
	 * 지역후기 삭제
	 */
	@Override
	public void deleteComment(int cno){
		commentDAO.deleteComment(cno);
	}

	/**
	 * 지역후기 등록갯수 반환
	 */
	@Override
	public int getTotalContentCount() {
		return commentDAO.getTotalContentCount();
	}
	
	/**
	 * 댓글 리스트 반환
	 */
	@Override
	public List<CommentReplyVO> getAllCommentReplyList(int clno) {
		return commentDAO.getAllCommentReplyList(clno);
	}
	/**
	 * 댓글 등록
	 */
	@Override
	public void insertNewCommentReply(CommentReplyVO rcvo){
		commentDAO.insertNewCommentReply(rcvo);
	}
	
	/**
	 * 다음 댓글번호 받아오기
	 */
	@Override
	public int getNextReplyNo(){
		return commentDAO.getNextReplyNo();
	}
	
	/**
	 * 부모댓글 정보 받아오기
	 */
	@Override
	public CommentReplyVO getParentInfo(int parent){
		return commentDAO.getParentInfo(parent);
	}
	
	/**
	 * 부모댓글 아이디 받아오기
	 */
	@Override
	public int getParentsParentId(int parent){
		return commentDAO.getParentsParentId(parent);
	}
	
	/**
	 * 댓글 삭제
	 */
	@Override
	public void deleteCommentReply(int rno){
		commentDAO.deleteCommentReply(rno);
	}
	
	/**
	 * 댓글 수정
	 */
	@Override
	public void updateCommentReply(CommentReplyVO crvo){
		commentDAO.updateCommentReply(crvo);
	}
	
	/**
	 * 댓글번호로 댓글 정보 반환
	 */
	@Override
	public CommentReplyVO getCommentReplyInfoByRNO(int rno){
		return commentDAO.getCommentReplyInfoByRNO(rno);
	}
	
	/**
	 * 부모댓글 삭제 시 자식댓글 삭제
	 */
	@Override
	public void deleteCommentReplyChild(int gno){
		commentDAO.deleteCommentReplyChild(gno);
	}

	@Override
	public CommentListVO findCommentListById(String id, String pageNo) {
		PagingBean pb = 
				new PagingBean(commentDAO.getTotalContentCountById(id), 
				Integer.parseInt(pageNo));
		return new CommentListVO(commentDAO.findCommentById(id, pb), pb);
	}

	/**
	 * 댓글 번호를 이용하여 댓글 반환
	 */
	@Override
	public CommentReplyVO showReply(int reno) {
		return commentDAO.showReply(reno);
	}

	@Override
	public String getPicNo() {
		return commentDAO.getPicNo();
	}

	@Override
	public void stackImg(String img_path, String picNo) {
		commentDAO.stackImg(img_path, picNo);
	}

	@Override
	public void clickLikeBtn(String cno, String id) {
		commentDAO.clickLikeBtn(cno, id);
	}

	@Override
	public int findLikeById(String cno, String id) {
		return commentDAO.findLikeById(cno, id);
	}

	@Override
	public int getCountLikeByCno(String cno) {
		return commentDAO.getCountLikeByCno(cno);
	}

	@Override
	public void unclickLikeBtn(String cno, String id) {
		commentDAO.unclickLikeBtn(cno, id);
	}

}