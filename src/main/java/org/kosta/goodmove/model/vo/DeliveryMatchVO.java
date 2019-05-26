/**
 * 
 */
package org.kosta.goodmove.model.vo;

/**
 * @author KOSTA
 *
 */
public class DeliveryMatchVO {
	private int bno;
	private String aid;
	private String did;
	private String state;
	
	public DeliveryMatchVO() {
		super();
	}

	public DeliveryMatchVO(int bno, String aid, String did, String state) {
		super();
		this.bno = bno;
		this.aid = aid;
		this.did = did;
		this.state = state;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "DeliveryMatchVO [bno=" + bno + ", aid=" + aid + ", did=" + did + ", state=" + state + "]";
	}
	
}
