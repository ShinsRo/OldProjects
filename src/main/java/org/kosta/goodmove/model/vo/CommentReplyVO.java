package org.kosta.goodmove.model.vo;

public class CommentReplyVO{
	
	private int rno;
	private int cno;
	private String id;
	private String name;
	private String time_posted;
	private int parent;
	private String content;
	private int gno;
	private int depth;
	private int order_no;
	public CommentReplyVO() {
		super();
	}
	public CommentReplyVO(int cno, String id, String name, int parent, String content) {
		super();
		this.cno = cno;
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.content = content;
	}
	public CommentReplyVO(int rno, int cno, String id, String name, String time_posted, int parent, String content,
			int gno, int depth, int order_no) {
		super();
		this.rno = rno;
		this.cno = cno;
		this.id = id;
		this.name = name;
		this.time_posted = time_posted;
		this.parent = parent;
		this.content = content;
		this.gno = gno;
		this.depth = depth;
		this.order_no = order_no;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime_posted() {
		return time_posted;
	}
	public void setTime_posted(String time_posted) {
		this.time_posted = time_posted;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getGno() {
		return gno;
	}
	public void setGno(int gno) {
		this.gno = gno;
	}
	public int getdepth() {
		return depth;
	}
	public void setdepth(int depth) {
		this.depth = depth;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	@Override
	public String toString() {
		return "CommentReplyVO [rno=" + rno + ", cno=" + cno + ", id=" + id + ", name=" + name + ", time_posted="
				+ time_posted + ", parent=" + parent + ", content=" + content + ", gno=" + gno + ", depth=" + depth
				+ ", order_no=" + order_no + "]";
	}
	

}
