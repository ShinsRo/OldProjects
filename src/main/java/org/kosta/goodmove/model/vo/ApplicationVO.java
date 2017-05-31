package org.kosta.goodmove.model.vo;
/**
 * 물려받기 신청서 정보를 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class ApplicationVO {
	private int ano;
	private String id;
	private String pnos;
	private String reason;
	public ApplicationVO() {
		super();
	}
	public ApplicationVO(int ano, String id, String pnos, String reason) {
		super();
		this.ano = ano;
		this.id = id;
		this.pnos = pnos;
		this.reason = reason;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPnos() {
		return pnos;
	}
	public void setPnos(String pnos) {
		this.pnos = pnos;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "ApplicationVO [ano=" + ano + ", id=" + id + ", pnos=" + pnos + ", reason=" + reason + "]";
	}
	
}
