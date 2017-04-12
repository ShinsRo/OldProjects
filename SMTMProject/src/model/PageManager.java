package model;

public class PageManager {
	private int allObjectCnt, groupSize;
	private int nowGroup = 1;
	private int boardSize = 5;
	private int pages, groups;
	private PagingBean pagingBean = new PagingBean();
	private static PageManager instance = new PageManager();

	public PageManager() {
		super();
	}

	public static PageManager getInstance(int allObjectCnt, int groupSize, int boardSize) {
		instance.allObjectCnt = allObjectCnt;
		instance.groupSize = groupSize;
		instance.boardSize = boardSize;
		return instance;
	}

	public static PageManager getInstance() {
		return instance;
	}

	public void setNowGroup(int pageNo) {
		nowGroup = (int) Math.ceil(pageNo / (double) groupSize);
		pagingBean.setStartPage((nowGroup - 1) * groupSize + 1);
		if (nowGroup < groups)
			pagingBean.setEndPage((nowGroup) * groupSize);
		else if (nowGroup == groups)
			pagingBean.setEndPage(pages);
		else {
			pagingBean.setEndPage(-1);
			pagingBean.setStartPage(-1);
		}
	}

	public int getAllObjectCnt() {
		return allObjectCnt;
	}

	public void setAllObjectCnt(int allObjectCnt) {
		this.allObjectCnt = allObjectCnt;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public PagingBean getPagingBean() {
		return pagingBean;
	}

	public PagingBean getPageBean(String pageNoStr) {
		int pageNo = Integer.parseInt(pageNoStr);
		pages = (int) Math.ceil(allObjectCnt / (double) boardSize);
		groups = (int) Math.ceil(pages / (double) groupSize);
		setNowGroup(pageNo);
		pagingBean.setNowPage(pageNo);
		pagingBean.setNowGroup(nowGroup);
		return pagingBean;

	}

}
