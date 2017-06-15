package org.kosta.goodmove.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.CommentPictureVO;
import org.kosta.goodmove.model.vo.CommentReplyVO;
import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.kosta.goodmove.model.vo.SearchVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAOImpl implements CommentDAO{
	@Resource
	 private SqlSessionTemplate template;

	/**
	 * 총 게시물의 수 조회, 반환
	 * @return 총 게시물 수
	 */
	@Override
	public int getTotalContentCount() {
		return template.selectOne("comment.getTotalContentCount");
	}

	/**
	 * 지역후기 글목록 조회, 반환
	 * @param 현재 페이지 번호 
	 * @return 현재 페이지에 출력될 글목록
	 */
	@Override
	public List<CommentVO> getCommentList(PagingBean pagingBean) {
		return template.selectList("comment.getCommentList", pagingBean);
	}
	/**
	 * 지역후기의 상세 내용 조회, 반환
	 * @param	글번호
	 * @return	조회된 글 상세 내용
	 */
	@Override
	public CommentVO showComment(int cno) {
		return template.selectOne("comment.showComment", cno);
	}

	/**
	 * 해당 번호에 해당하는 지역후기의 조회수 증가
	 * @param	글번호
	 */
	@Override
	public void updateCount(int clno) {
		template.update("comment.updateCount",clno);
	}

	/**
	 * 지역후기 내용 수정
	 * @param	글번호	
	 */
	@Override
	public void commentUpdate(CommentVO cvo) {
		template.update("comment.commentUpdate", cvo);
	}

	/**
	 * 글작성
	 * @param	고객이 작성한 지역후기 내용
	 */
	@Override
	public void commentRegister(CommentVO cvo) {
		template.insert("comment.commentRegister", cvo);
	}
	
	/**
	 * 검색 시 검색어에 해당하는 작성물의 갯수 받아오기
	 */
	@Override
	public int getSearchContentCount(SearchVO svo) {
		int count = template.selectOne("comment.getSearchContentCount", svo);
		return count;
	}
	
	/**
	 * 지역후기 삭제
	 */
	@Override
	public void deleteComment(int cno){
		template.delete("comment.deleteComment", cno);
	}
	
	/**
	 * 전체 댓글 리스트 반환
	 */
	@Override
	public List<CommentReplyVO> getAllCommentReplyList(int cno){
		return template.selectList("comment.getAllCommentReplyList",cno);
	}
	
	/** 
	 * 댓글 작성
	 */
	@Override
	public void insertNewCommentReply(CommentReplyVO rcvo){
		template.insert("comment.insertNewCommentReply",rcvo);
	}
	
	/**
	 * 다음 댓글번호 반환
	 */
	@Override
	public int getNextReplyNo(){
		return template.selectOne("comment.getNextReplyNo");
	}
	
	/**
	 * 부모댓글 정보 반환
	 */
	@Override
	public CommentReplyVO getParentInfo(int parent){
		return template.selectOne("comment.getParentInfo",parent);
	}
	
	/**
	 * 부모댓글 번호 반환
	 */
	@Override
	public int getParentsParentId(int parent){
		return template.selectOne("comment.getParentsParentId",parent);
	}
	
	/**
	 * 댓글 삭제
	 */
	@Override
	public void deleteCommentReply(int rno){
		template.delete("comment.deleteCommentReply",rno);
	}
	
	/**
	 * 댓글 수정
	 */
	@Override
	public void updateCommentReply(CommentReplyVO crvo){
		template.update("comment.updateCommentReply",crvo);
	}
	
	/**
	 * 번호로 댓글 정보 반환
	 */
	@Override
	public CommentReplyVO getCommentReplyInfoByRNO(int rno){
		return template.selectOne("comment.getCommentReplyInfoByRNO",rno);
	}
	
	/**
	 * 부모댓글 삭제 시 자녀댓글 삭제
	 */
	@Override
	public void deleteCommentReplyChild(int gno){
		template.delete("comment.deleteCommentReplyChild",gno);
	}
	

	/**
	 * 아이디에 해당하는 지역후기 작성갯수 반환
	 */
	@Override
	public int getTotalContentCountById(String id) {
		return template.selectOne("comment.getTotalContentCountById", id);
	}

	/**
	 * 아이디에 해당하는 지역후기 리스트 반환
	 */
	@Override
	public List<CommentVO> findCommentById(String id, PagingBean pb) {
		HashMap<String, Object> param = new HashMap<>();
		System.out.println(id + "---"+pb.getStartRowNumber());
		param.put("id", id);
		param.put("pagingBean", pb);
		return template.selectList("comment.findCommentById", param);
	}

	/**
	 * 댓글번호 이용하여 댓글 반환
	 */
	@Override
	public CommentReplyVO showReply(int reno) {
		return template.selectOne("comment.findCommentReplyByNo", reno);
	}

	@Override
	public String getPicNo() {
		return template.selectOne("comment.getPicNo");
	}

	@Override
	public void stackImg(CommentPictureVO cpvo) {
		System.out.println(cpvo);
		template.insert("comment.stackImg", cpvo);
	}

	@Override
	public void clickLikeBtn(String cno, String id) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("cno", cno);
		paramMap.put("id", id);
		template.insert("comment.clickLikeBtn", paramMap);
	}

	@Override
	public int findLikeById(String cno, String id) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("cno", cno);
		paramMap.put("id", id);
		return template.selectOne("comment.findLikeById", paramMap);
	}

	@Override
	public int getCountLikeByCno(String cno) {
		return template.selectOne("comment.getCountLikeByCno", cno);
	}

	@Override
	public String getImgPath(CommentPictureVO cpvo) {
		return template.selectOne("comment.getImgPath", cpvo);
	}
	

}
