package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.CommentDAO;
import org.kosta.goodmove.model.vo.CommentListVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
	@Resource
	private CommentDAO commentDAO;

	/**
	 * 페이지 정보가 없이 리스트 요청 시 
	 * 페이지를 1로 설정 후 오버로딩 된 메서드로 이동
	 */
	@Override
	public CommentListVO getCommentList() {
		return getCommentList("1");
	}

	/**
	 * 페이지 정보가 있는 지역후기 리스트 반환
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
	 * 지역후기 상세 내용 반환
	 * hit수 증가됨
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
	 * 지역후기 상세정보 반환
	 * hit수 증가되지 않음
	 */
	@Override
	public CommentVO showCommentNoHit(int clno) {
		return commentDAO.showComment(clno);
	}

	/**
	 * 지역후기 상세정보 수정
	 */
	@Override
	public void updateBoard(CommentVO cvo) {
		commentDAO.commentUpdate(cvo);
		
	}

	@Override
	public void commentRegister(CommentVO cvo) {
		commentDAO.commentRegister(cvo);
		
	}
}