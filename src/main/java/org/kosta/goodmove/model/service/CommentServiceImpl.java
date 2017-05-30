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
	
	@Override
	public void deleteComment(int cno){
		commentDAO.deleteComment(cno);
	}

	@Override
	public int getTotalContentCount() {
		return commentDAO.getTotalContentCount();
	}
	
	
	@Override
	public List<CommentReplyVO> getAllCommentReplyList(int clno) {
		return commentDAO.getAllCommentReplyList(clno);
	}
	@Override
	public void insertNewCommentReply(CommentReplyVO rcvo){
		commentDAO.insertNewCommentReply(rcvo);
	}
	@Override
	public int getNextReplyNo(){
		return commentDAO.getNextReplyNo();
	}
	
	@Override
	public CommentReplyVO getParentInfo(int parent){
		return commentDAO.getParentInfo(parent);
	}
	@Override
	public int getParentsParentId(int parent){
		return commentDAO.getParentsParentId(parent);
	}
	
	@Override
	public void deleteCommentReply(int rno){
		commentDAO.deleteCommentReply(rno);
	}
	
	@Override
	public void updateCommentReply(CommentReplyVO crvo){
		commentDAO.updateCommentReply(crvo);
	}
}