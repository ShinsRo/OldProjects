package org.kosta.goodmove.model.vo;
/**
 * 물려주기 물건 각각 대한 정보를 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class ProductVO {
	private String pno;
	private String pname;
	private String kind;
	private String pcontent;
	private int bno;
	private int addr_code;
	public ProductVO() {
		super();
	}
	public ProductVO(String pno, String pname, String kind, String pcontent, int bno, int addr_code) {
		super();
		this.pno = pno;
		this.pname = pname;
		this.kind = kind;
		this.pcontent = pcontent;
		this.bno = bno;
		this.addr_code = addr_code;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
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
	@Override
	public String toString() {
		return "ProductVO [pno=" + pno + ", pname=" + pname + ", kind=" + kind + ", pcontent=" + pcontent + ", bno="
				+ bno + ", addr_code=" + addr_code + "]";
	}
	
}
