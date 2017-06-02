package org.kosta.goodmove.model.vo;

import java.util.List;

/**
 * 물려받기 신청서 정보를 저장 : Value Object
 * 
 * @author AreadyDoneTeam
 * @version 1
 */
public class ApplicationVO {
	private int ano;
	private int bno;
	private String id;
	private String pnos;
	private String reason;
	private String is_selected;
	private List<ProductVO> pList;

	public ApplicationVO() {
		super();
	}

	public ApplicationVO(int ano, int bno, String id, String pnos, String reason) {
		super();
		this.ano = ano;
		this.bno = bno;
		this.id = id;
		this.pnos = pnos;
		this.reason = reason;
	}

	public List<ProductVO> getpList() {
		return pList;
	}

	public void setpList(List<ProductVO> pList) {
		this.pList = pList;
	}

	public String getIs_selected() {
		return is_selected;
	}

	public void setIs_selected(String is_selected) {
		this.is_selected = is_selected;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
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
		return "ApplicationVO [ano=" + ano + ", bno=" + bno + ", id=" + id + ", pnos=" + pnos + ", reason=" + reason
				+ ", is_selected=" + is_selected + ", pList=" + pList + "]";
	}

}