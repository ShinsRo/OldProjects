package org.kosta.goodmove.model.vo;
/**
 * 지역 후기 정보를 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class CommentVO {
	private String cno;
	private String content;
	private String addr;
	private String title;
	private int hit;
	private String id;
	private String time_posted;
	public CommentVO() {
		super();
	}
	public CommentVO(String cno, String content, String addr, String title, int hit, String id, String time_posted) {
		super();
		this.cno = cno;
		this.content = content;
		this.addr = addr;
		this.title = title;
		this.hit = hit;
		this.id = id;
		this.time_posted = time_posted;
	}
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime_posted() {
		return time_posted;
	}
	public void setTime_posted(String time_posted) {
		this.time_posted = time_posted;
	}
	@Override
	public String toString() {
		return "CommentVO [cno=" + cno + ", content=" + content + ", addr=" + addr + ", title=" + title + ", hit=" + hit
				+ ", id=" + id + ", time_posted=" + time_posted + "]";
	}
	
}
