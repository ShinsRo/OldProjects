package model;

public class CommentVO {
	private int comNO;
	private String content;
	private int depth;
	private int parrentComNO;
	public CommentVO() {
		super();
	}

	public CommentVO(int comNO, String content, int depth, int parrentComNO) {
		super();
		this.comNO = comNO;
		this.content = content;
		this.depth = depth;
		this.parrentComNO = parrentComNO;
	}

	public int getParrentComNO() {
		return parrentComNO;
	}

	public void setParrentComNO(int parrentComNO) {
		this.parrentComNO = parrentComNO;
	}

	public int getComNO() {
		return comNO;
	}
	public void setComNO(int comNO) {
		this.comNO = comNO;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	@Override
	public String toString() {
		return "CommentVO [comNO=" + comNO + ", content=" + content + ", depth=" + depth + ", parrentComNO="
				+ parrentComNO + "]";
	}	
}
