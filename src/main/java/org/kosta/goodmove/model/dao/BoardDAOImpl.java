package org.kosta.goodmove.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.BoardPagingBean;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
/**
 * 메타 정보 관할 컨트롤러 : DAO
 * @author AreadyDoneTeam
 * @version 1
 */
@Repository
public class BoardDAOImpl implements BoardDAO{
 @Resource
 private SqlSessionTemplate template;
 	/**
 	 * 다음 bno 받아오기
 	 *  @return B_SEQ.nextVal
 	 */
	 @Override
	 public int getNextBno(){
		 return template.selectOne("board.getNextBno");
	 }
	 @Override
	 public int getNextPno(){
		 return template.selectOne("board.getNextPno");
	 }
	 /**
	  * 모든 board 게시글 조회
	  * @param pagingbean
	  * @return Board 리스트
	  */
	@Override
	public List<BoardVO> getAllBoardList(BoardPagingBean pagingBean) {
		return template.selectList("board.getAllBoardList", pagingBean);
	}
	/**
	 * 총 board 글 갯수
	 * @return 총 글 갯수
	 */
	@Override
	public int getTotalBoardCount(){
		return template.selectOne("board.getTotalBoardCount");
	}
	@Override
	public int getTotalBoardCount(String id) {
		return template.selectOne("board.getTotalBoardCountById", id);
	}
	/**
	 * 글번호의 board 디테일
	 * return BoardVO
	 */
	@Override
	public BoardVO getBoardDetailByBno(int bno){
		return template.selectOne("board.getBoardDetailByBno", bno);
	}
	
	/**
	 * board등록 후 product 순차 등록
	 * @
	 */
	@Override
	public void boardRegister(BoardVO bvo) {
		System.out.println(bvo);
		template.insert("board.boardRegister", bvo);
		for(ProductVO pvo : bvo.getpList()){
			template.insert("board.productRegister", pvo);
		}
	}
	@Override
	public List<ProductVO> getProductImgByBno(int bno){
		return template.selectList("board.getProductImgByBno", bno);
	}
	@Override
	public List<BoardVO> getMyBoardList(BoardPagingBean pagingBean, String id) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pagingBean", pagingBean);
		paramMap.put("id", id);
		return template.selectList("board.getMyBoardList", paramMap);
	}
}
