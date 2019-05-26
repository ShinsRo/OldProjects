package org.kosta.goodmove.model.dao;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.BoardPagingBean;
import org.kosta.goodmove.model.vo.BoardVO;
import org.kosta.goodmove.model.vo.ProductVO;
import org.kosta.goodmove.model.vo.SearchVO;
import org.kosta.goodmove.model.vo.TransactionVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

/**
 * 메타 정보 관할 컨트롤러 : DAO
 * 
 * @author AreadyDoneTeam
 * @version 1
 */
@Repository
public class BoardDAOImpl implements BoardDAO {
	@Resource
	private SqlSessionTemplate template;

	/**
	 * 다음 bno 받아오기
	 * 
	 * @return B_SEQ.nextVal
	 */
	@Override
	public int getNextBno() {
		return template.selectOne("board.getNextBno");
	}

	@Override
	public int getNextPno() {
		return template.selectOne("board.getNextPno");
	}

	@Override
	public int getNextAno() {
		return template.selectOne("board.getNextAno");
	}

	@Override
	public int getNextTno() {
		return template.selectOne("board.getNextTno");
	}

	/**
	 * 모든 board 게시글 조회
	 * 
	 * @param pagingbean
	 * @return Board 리스트
	 */
	@Override
	public List<BoardVO> getAllBoardList(BoardPagingBean pagingBean) {
		return template.selectList("board.getAllBoardList", pagingBean);
	}

	/**
	 * 총 board 글 갯수
	 * 
	 * @return 총 글 갯수
	 */
	@Override
	public int getTotalBoardCount() {
		return template.selectOne("board.getTotalBoardCount");
	}

	@Override
	public int getTotalBoardCount(String id) {
		return template.selectOne("board.getTotalBoardCountById", id);
	}

	/**
	 * 글번호의 board 디테일 return BoardVO
	 */
	@Override
	public BoardVO getBoardDetailByBno(int bno) {
		return template.selectOne("board.getBoardDetailByBno", bno);
	}

	/**
	 * board등록 후 product 순차 등록 @
	 */
	@Override
	public void boardRegister(BoardVO bvo) {
		template.insert("board.boardRegister", bvo);
		for (ProductVO pvo : bvo.getpList()) {

			template.insert("board.productRegister", pvo);
		}
	}

	@Override
	public List<ProductVO> getProductImgByBno(int bno) {
		return template.selectList("board.getProductImgByBno", bno);
	}

	@Override
	public void registerApplication(ApplicationVO avo) {
		template.insert("board.registerApplication", avo);
	}

	@Override
	public void registerTransaction(TransactionVO tvo) {
		template.insert("board.registerTransaction", tvo);
	}

	@Override
	public List<BoardVO> getMyBoardList(BoardPagingBean pagingBean, String id) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pagingBean", pagingBean);
		paramMap.put("id", id);
		return template.selectList("board.getMyBoardList", paramMap);
	}

	@Override
	public List<ApplicationVO> getApplications(int bno) {
		return template.selectList("board.getApplications", bno);
	}

	@Override
	public int isGiveMeChecked(ApplicationVO avo) {
		return template.selectOne("board.isGiveMeChecked", avo);
	}

	@Override
	public List<ApplicationVO> getApplicationsById(String id) {
		return template.selectList("board.getApplicationsById", id);
	}

	@Override
	public int getSearchContentCount(SearchVO svo) {
		int count = template.selectOne("board.getSearchContentCount", svo);
		return count;
	}

	@Override
	public ProductVO getProductByPno(String pno) {
		return template.selectOne("board.getProductByPno", pno);
	}

	@Override
	public void confirmApply(String bno, String id) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("bno", bno);
		paramMap.put("id", id);
		template.update("board.confirmApply", paramMap);
	}

	@Override
	public void nowUnavailable(String pno) {
		System.out.println("nowUnavailable");
		template.update("board.nowUnavailable", pno);
	}

	@Override
	public void Refresh(int bno) {
		template.update("board.Refresh", bno);
	}

	@Override
	public int selectedProductCnt(int bno) {
		return template.selectOne("board.selectedProductCnt", bno);
	}
	/**
	 * 아이디를 통해 글을 올린수를 
	 * 가져오는 DAOImpl
	 */
	@Override
	public int getTotalContentCountById(String id) {
		return template.selectOne("board.getTotalContentCountById", id);
	}
	/**
	 * 아이디를 통해 내가올린 드려요로
	 * 연결되기 위한 DAOImpl
	 */
	@Override
	public List<BoardVO> boardListById(String id, BoardPagingBean bpb) {
		HashMap<String, Object> param = new HashMap<>();
		System.out.println(id + "---"+bpb.getStartRowNumber());
		param.put("id", id);
		param.put("pagingBean", bpb);
		return template.selectList("board.boardListById", param);
	}

	@Override
	public ApplicationVO getApplicationByPk(String bno, String id) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("bno", bno);
		paramMap.put("id", id);
		return template.selectOne("board.getApplicationByPk", paramMap);
	}

	@Override
	public void putItOnDelivery(String bno, String id) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("bno", bno);
		paramMap.put("id", id);
		template.update("board.putItOnDelivery", paramMap);
	}

	@Override
	public void deleteBoard(String bno) {
		template.update("board.deleteBoard", bno);
	}

	@Override
	public void deletePoduct(String bno) {
		template.update("board.deleteProduct", bno);
	}

	@Override
	public void deleteApplication(String bno) {
		template.update("board.deleteApplication", bno);
	}

	@Override
	public void disableOtherApps(String bno, String pno) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("bno", bno);
		paramMap.put("pno", pno+",");
		template.update("board.disableOtherApps", paramMap);
	}

	@Override
	public int getProductCnt(int bno) {
		return template.selectOne("board.getProductCnt", bno);
	}
//----------------
	@Override
	public void productRegister(ProductVO tempPVO) {
		template.insert("board.productRegister", tempPVO);
	}

	@Override
	public void productUpdate(ProductVO tempPVO) {
		template.update("board.productUpdate", tempPVO);
	}

	@Override
	public void boardUpdate(BoardVO bvo) {
		template.update("board.boardUpdate", bvo);
	}

	@Override
	public void productDelete(String pno) {
		template.delete("board.productDelete", pno);
	}

	@Override
	public void updateBoardHit(int bno) {
		template.update("board.updateBoardHit", bno);
	}
}
