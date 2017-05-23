package org.kosta.goodmove.model.vo;
/**
 * 물려주기 신청 현황을 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class TransactionVO {
	private String tno;
	private String ano;
	private String pno;
	private String id;
	public TransactionVO() {
		super();
	}
	public TransactionVO(String tno, String ano, String pno, String id) {
		super();
		this.tno = tno;
		this.ano = ano;
		this.pno = pno;
		this.id = id;
	}
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "TransactionVO [tno=" + tno + ", ano=" + ano + ", pno=" + pno + ", id=" + id + "]";
	}
	
}
