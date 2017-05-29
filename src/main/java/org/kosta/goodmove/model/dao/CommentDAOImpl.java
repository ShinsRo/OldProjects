package org.kosta.goodmove.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.CommentVO;
import org.kosta.goodmove.model.vo.PagingBean;
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
	
	@Override
	public void deleteComment(int cno){
		template.delete("comment.deleteComment", cno);
	}
}
