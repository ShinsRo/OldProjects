package model;

public class PagingBean {
	private int startPage;
	private int nowPage = 1;
	private int endPage;
	private int nowGroup = 1;
	public PagingBean() {
		super();
	}
	
	public PagingBean(int startPage, int nowPage, int endPage) {
		super();
		this.startPage = startPage;
		this.nowPage = nowPage;
		this.endPage = endPage;
		
	}
	/**
	 * @return the startPage
	 */
	public int getStartPage() {
		return startPage;
	}
	/**
	 * @param startPage the startPage to set
	 */
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	/**
	 * @return the nowPage
	 */
	public int getNowPage() {
		return nowPage;
	}
	/**
	 * @param nowPage the nowPage to set
	 */
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	/**
	 * @return the endPage
	 */
	public int getEndPage() {
		return endPage;
	}
	/**
	 * @param endPage the endPage to set
	 */
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PagingBean [startPage=" + startPage + ", nowPage=" + nowPage + ", endPage=" + endPage + "]";
	}

	public int getNowGroup() {
		return nowGroup;
	}

	public void setNowGroup(int nowGroup) {
		this.nowGroup = nowGroup;
	}
	
}
