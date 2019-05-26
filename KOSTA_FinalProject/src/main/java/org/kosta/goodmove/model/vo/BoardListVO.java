package org.kosta.goodmove.model.vo;

import java.util.List;
/**
 * board list 와 pagingbean 가지고 있는 클래스
 * @author garin
 *
 */
public class BoardListVO {
	private List<BoardVO> list;
	private BoardPagingBean pagingBean;
	public BoardListVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardListVO(List<BoardVO> list, BoardPagingBean pagingBean) {
		super();
		this.list = list;
		this.pagingBean = pagingBean;
	}
	public List<BoardVO> getList() {
		return list;
	}
	public void setList(List<BoardVO> list) {
		this.list = list;
	}
	public BoardPagingBean getPagingBean() {
		return pagingBean;
	}
	public void setPagingBean(BoardPagingBean pagingBean) {
		this.pagingBean = pagingBean;
	}
	@Override
	public String toString() {
		return "BoardListVO [list=" + list + ", pagingBean=" + pagingBean + "]";
	}
	
}
