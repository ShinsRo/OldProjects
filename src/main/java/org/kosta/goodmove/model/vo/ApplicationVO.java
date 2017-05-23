package org.kosta.goodmove.model.vo;
/**
 * 물려받기 신청서 정보를 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class ApplicationVO {
	private String ano;
	private String id;
	private String reason;
	public ApplicationVO() {
		super();
	}
	public ApplicationVO(String ano, String id, String reason) {
		super();
		this.ano = ano;
		this.id = id;
		this.reason = reason;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "ApplicationVO [ano=" + ano + ", id=" + id + ", reason=" + reason + "]";
	}
	
}
