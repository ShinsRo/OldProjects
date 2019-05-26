package org.kosta.goodmove.model.vo;

public class CommentPictureVO {
	private int picno; 
	private String img_path;
	private int pic_cursor;
	
	public CommentPictureVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentPictureVO(int picno, String img_path) {
		super();
		this.picno = picno;
		this.img_path = img_path;
	}
	
	public CommentPictureVO(int picno, String img_path, int pic_cursor) {
		super();
		this.picno = picno;
		this.img_path = img_path;
		this.pic_cursor = pic_cursor;
	}
	public int getPicno() {
		return picno;
	}
	public void setPicno(int picno) {
		this.picno = picno;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	
	public int getPic_cursor() {
		return pic_cursor;
	}
	public void setPic_cursor(int pic_cursor) {
		this.pic_cursor = pic_cursor;
	}
	
	@Override
	public String toString() {
		return "CommentPictureVO [picno=" + picno + ", img_path=" + img_path + ", pic_cursor=" + pic_cursor + "]";
	}
	
	
}
