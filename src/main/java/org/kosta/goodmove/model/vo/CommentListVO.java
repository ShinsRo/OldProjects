package org.kosta.goodmove.model.vo;

import java.util.List;

/**
 * 게시물 리스트 정보와 페이징 정보를 가지고 있는 클래스 
 * @author cj-nt
 *
 */
public class CommentListVO {

	private List<CommentVO> list;
	private PagingBean pagingBean;
	
	public CommentListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentListVO(List<CommentVO> list, PagingBean pagingBean) {
		super();
		this.list = list;
		this.pagingBean = pagingBean;
	}

	public List<CommentVO> getList() {
		return list;
	}

	public void setList(List<CommentVO> list) {
		this.list = list;
	}

	public PagingBean getPagingBean() {
		return pagingBean;
	}

	public void setPagingBean(PagingBean pagingBean) {
		this.pagingBean = pagingBean;
	}

	@Override
	public String toString() {
		return "CommentListVO [list=" + list + ", pagingBean=" + pagingBean + "]";
	}
	
}
