package org.kosta.goodmove.model.vo;
/**
 * 물려주기 물건 각각 대한 정보를 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class ProductVO {
	private int pno;
	private String ptitle;
	private String kind;
	private String pcontent;
	private String img_path;
	private int bno;
	public ProductVO() {
		super();
	}
	public ProductVO(int pno, String ptitle, String kind, String pcontent, String img_path, int bno) {
		super();
		this.pno = pno;
		this.ptitle = ptitle;
		this.kind = kind;
		this.pcontent = pcontent;
		this.img_path = img_path;
		this.bno = bno;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getPtitle() {
		return ptitle;
	}
	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String thumbPath) {
		this.img_path = thumbPath;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	@Override
	public String toString() {
		return "ProductVO [pno=" + pno + ", ptitle=" + ptitle + ", kind=" + kind + ", pcontent=" + pcontent
				+ ", img_path=" + img_path + ", bno=" + bno + "]";
	}
	
}
