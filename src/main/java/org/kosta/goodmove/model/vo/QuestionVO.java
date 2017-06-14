package org.kosta.goodmove.model.vo;

public class QuestionVO {
	public String qno;
	public String title;
	public String hit;
	public String time_posted;
	public String id;
	public String content;
	public String is_secret;
	public int re_ref;
	public int re_lev;
	public int re_seq;
	public int q_parent;
	public QuestionVO() {
		super();
	}
	public QuestionVO(String qno, String title, String hit, String time_posted, String id, String content,
			String is_secret, int re_ref, int re_lev, int re_seq, int q_parent) {
		super();
		this.qno = qno;
		this.title = title;
		this.hit = hit;
		this.time_posted = time_posted;
		this.id = id;
		this.content = content;
		this.is_secret = is_secret;
		this.re_ref = re_ref;
		this.re_lev = re_lev;
		this.re_seq = re_seq;
		this.q_parent = q_parent;
	}
	public String getQno() {
		return qno;
	}
	public void setQno(String qno) {
		this.qno = qno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getTime_posted() {
		return time_posted;
	}
	public void setTime_posted(String time_posted) {
		this.time_posted = time_posted;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIs_secret() {
		return is_secret;
	}
	public void setIs_secret(String is_secret) {
		this.is_secret = is_secret;
	}
	public int getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}
	public int getRe_lev() {
		return re_lev;
	}
	public void setRe_lev(int re_lev) {
		this.re_lev = re_lev;
	}
	public int getRe_seq() {
		return re_seq;
	}
	public void setRe_seq(int re_seq) {
		this.re_seq = re_seq;
	}
	public int getQ_parent() {
		return q_parent;
	}
	public void setQ_parent(int q_parent) {
		this.q_parent = q_parent;
	}
	@Override
	public String toString() {
		return "QuestionVO [qno=" + qno + ", title=" + title + ", hit=" + hit + ", time_posted=" + time_posted + ", id="
				+ id + ", content=" + content + ", is_secret=" + is_secret + ", re_ref=" + re_ref + ", re_lev=" + re_lev
				+ ", re_seq=" + re_seq + ", q_parent=" + q_parent + "]";
	}
	
}
	
