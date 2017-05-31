package org.kosta.goodmove.model.vo;

/**
 * 물려주기 신청 현황을 저장 : Value Object
 * 
 * @author AreadyDoneTeam
 * @version 1
 */
public class TransactionVO {
	private int tno;
	private int ano;
	private int bno;
	private String id;

	public TransactionVO() {
		super();

	}

	public TransactionVO(int tno, int ano, int bno, String id) {
		super();
		this.tno = tno;
		this.ano = ano;
		this.bno = bno;
		this.id = id;
	}

	public int getTno() {
		return tno;
	}

	public void setTno(int tno) {
		this.tno = tno;
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

	@Override
	public String toString() {
		return "TransactionVO [tno=" + tno + ", ano=" + ano + ", bno=" + bno + ", id=" + id + "]";
	}

}
