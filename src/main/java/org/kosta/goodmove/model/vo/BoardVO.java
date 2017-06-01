package org.kosta.goodmove.model.vo;

import java.util.List;

/**
 * 게시 기본 단위로서 물려줄 물건 정보를 저장 : Value Object
 * 
 * @author AreadyDoneTeam
 * @version 1
 */
public class BoardVO {
	private int bno;
	private int hit;
	private String title;
	private String id;
	private String addr;
	private String bcontent;
	private String time_posted;
	private String thumbPath;
	private List<ProductVO> pList;
	private List<ApplicationVO> aList;
	public BoardVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardVO(int bno, int hit, String title, String id, String addr, String bcontent, String time_posted,
			String thumbPath, List<ProductVO> pList) {
		super();
		this.bno = bno;
		this.hit = hit;
		this.title = title;
		this.id = id;
		this.addr = addr;
		this.bcontent = bcontent;
		this.time_posted = time_posted;
		this.thumbPath = thumbPath;
		this.pList = pList;
	}
	
	public List<ApplicationVO> getaList() {
		return aList;
	}
	public void setaList(List<ApplicationVO> aList) {
		this.aList = aList;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public String getTime_posted() {
		return time_posted;
	}
	public void setTime_posted(String time_posted) {
		this.time_posted = time_posted;
	}
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	public List<ProductVO> getpList() {
		return pList;
	}
	public void setpList(List<ProductVO> pList) {
		this.pList = pList;
	}
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", hit=" + hit + ", title=" + title + ", id=" + id + ", addr=" + addr
				+ ", bcontent=" + bcontent + ", time_posted=" + time_posted + ", thumbPath=" + thumbPath + ", pList="
				+ pList + "]";
	}
}
