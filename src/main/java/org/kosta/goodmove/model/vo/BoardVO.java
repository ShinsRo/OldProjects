package org.kosta.goodmove.model.vo;
/**
 * 게시 기본 단위로서 물려줄 물건 정보를 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class BoardVO {
	private int bno;
	private int addr_code;
	private int hit;
	private String title;
	private String id;
	private String addr;
	private String bcontent;
	private String time_posted;
	public BoardVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardVO(int bno, int addr_code, int hit, String title, String id, String addr, String bcontent,
			String time_posted) {
		super();
		this.bno = bno;
		this.addr_code = addr_code;
		this.hit = hit;
		this.title = title;
		this.id = id;
		this.addr = addr;
		this.bcontent = bcontent;
		this.time_posted = time_posted;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getAddr_code() {
		return addr_code;
	}
	public void setAddr_code(int addr_code) {
		this.addr_code = addr_code;
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
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", addr_code=" + addr_code + ", hit=" + hit + ", title=" + title + ", id=" + id
				+ ", addr=" + addr + ", bcontent=" + bcontent + ", time_posted=" + time_posted + "]";
	}
	

}
