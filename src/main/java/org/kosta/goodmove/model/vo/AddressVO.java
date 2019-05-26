package org.kosta.goodmove.model.vo;
/**
 * 주소 정보를 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class AddressVO {
	private int addr_code;
	private String cno;
	private String addr_detail;
	public AddressVO() {
		super();
	}
	public AddressVO(int addr_code, String cno, String addr_detail) {
		super();
		this.addr_code = addr_code;
		this.cno = cno;
		this.addr_detail = addr_detail;
	}
	public int getAddr_code() {
		return addr_code;
	}
	public void setAddr_code(int addr_code) {
		this.addr_code = addr_code;
	}
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
	@Override
	public String toString() {
		return "AddressVO [addr_code=" + addr_code + ", cno=" + cno + ", addr_detail=" + addr_detail + "]";
	}
	
}
