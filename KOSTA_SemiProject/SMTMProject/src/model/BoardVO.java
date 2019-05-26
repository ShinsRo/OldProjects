package model;

import java.util.ArrayList;

public class BoardVO {
	private int boardNO;
	private String title;
	private String content;
	private String timePosted;
	private String id;
	private ArrayList<CommentVO> commentList;

	public BoardVO() {
		super();
	}
	
	public BoardVO(int boardNO, String title, String content, String timePosted, String id,
			ArrayList<CommentVO> commentList) {
		super();
		this.boardNO = boardNO;
		this.title = title;
		this.content = content;
		this.timePosted = timePosted;
		this.id = id;
		this.commentList = commentList;
	}
	public int getBoardNO() {
		return boardNO;
	}
	public void setBoardNO(int boardNO) {
		this.boardNO = boardNO;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTimePosted() {
		return timePosted;
	}
	public void setTimePosted(String timePosted) {
		this.timePosted = timePosted;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public ArrayList<CommentVO> getCommentList() {
		return this.commentList;
	}
	public void setCommentList(ArrayList<CommentVO> commentList) {
		this.commentList = commentList;
	}
	@Override
	public String toString() {
		return "BoardVO [boardNO=" + boardNO + ", title=" + title + ", content=" + content + ", timePosted="
				+ timePosted + ", id=" + id + ", CommentList=" + commentList + "]";
	}

}
