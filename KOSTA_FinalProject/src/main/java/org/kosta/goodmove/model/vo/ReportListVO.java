package org.kosta.goodmove.model.vo;

import java.util.List;

public class ReportListVO {
	private List<ReportVO> list;
	private ReportPagingBean pagingBean;
	public ReportListVO() {
		super();
	}
	public ReportListVO(List<ReportVO> list, ReportPagingBean pagingBean) {
		super();
		this.list = list;
		this.pagingBean = pagingBean;
	}
	public List<ReportVO> getList() {
		return list;
	}
	public void setList(List<ReportVO> list) {
		this.list = list;
	}
	public ReportPagingBean getPagingBean() {
		return pagingBean;
	}
	public void setPagingBean(ReportPagingBean pagingBean) {
		this.pagingBean = pagingBean;
	}
	@Override
	public String toString() {
		return "ReportListVO [list=" + list + ", pagingBean=" + pagingBean + "]";
	}
	
}
