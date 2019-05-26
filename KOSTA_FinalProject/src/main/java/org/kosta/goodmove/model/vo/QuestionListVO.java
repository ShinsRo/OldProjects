package org.kosta.goodmove.model.vo;

import java.util.List;

/**
 * 게시물 리스트 정보와 페이징 정보를 가지고 있는 클래스
 * 
 * @author cj-nt
 *
 */
public class QuestionListVO {

	private List<QuestionVO> list;
	private PagingBean pagingBean;

	public QuestionListVO() {
		super();
	}

	public QuestionListVO(List<QuestionVO> list, PagingBean pagingBean) {
		super();
		this.list = list;
		this.pagingBean = pagingBean;
	}

	public List<QuestionVO> getList() {
		return list;
	}

	public void setList(List<QuestionVO> list) {
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
		return "QuestionListVO [list=" + list + ", pagingBean=" + pagingBean + "]";
	}

}
