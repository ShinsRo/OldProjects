package org.kosta.goodmove.model.vo;

import java.util.List;

/**
 * 물려받기 신청서 정보를 저장 : Value Object
 * 
 * @author AreadyDoneTeam
 * @version 1
 */
public class ApplicationVO {
	private int bno;
	private String reason;
	private String pnos;
	private String id;
	private String is_selected;
	private String is_delivery;
	private List<ProductVO> pList;

	public ApplicationVO() {
		super();
	}

	public ApplicationVO(int bno, String reason, String pnos, String id, String is_selected, String is_delivery,
			List<ProductVO> pList) {
		super();
		this.bno = bno;
		this.reason = reason;
		this.pnos = pnos;
		this.id = id;
		this.is_selected = is_selected;
		this.is_delivery = is_delivery;
		this.pList = pList;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPnos() {
		return pnos;
	}

	public void setPnos(String pnos) {
		this.pnos = pnos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIs_selected() {
		return is_selected;
	}

	public void setIs_selected(String is_selected) {
		this.is_selected = is_selected;
	}

	public String getIs_delivery() {
		return is_delivery;
	}

	public void setIs_delivery(String is_delivery) {
		this.is_delivery = is_delivery;
	}

	public List<ProductVO> getpList() {
		return pList;
	}

	public void setpList(List<ProductVO> pList) {
		this.pList = pList;
	}

	@Override
	public String toString() {
		return "ApplicationVO [bno=" + bno + ", reason=" + reason + ", pnos=" + pnos + ", id=" + id + ", is_selected="
				+ is_selected + ", is_delivery=" + is_delivery + ", pList=" + pList + "]";
	}

}