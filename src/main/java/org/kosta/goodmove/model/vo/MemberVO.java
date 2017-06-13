package org.kosta.goodmove.model.vo;
/**
 * 회원 정보를 저장 : Value Object
 * @author AreadyDoneTeam
 * @version 1
 */
public class MemberVO {
	private String id;
	private String password;
	private String name;
	private String addr;
	private String addr_detail;
	private String tel;
	private String job;
	private String enabled;
	public MemberVO() {
		super();
	}
	

	public MemberVO(String id, String name, String tel) {
		super();
		this.id = id;
		this.name = name;
		this.tel = tel;
	}

	public MemberVO(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public MemberVO(String id, String password, String name, String addr, String addr_detail, String tel, String job) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.addr = addr;
		this.addr_detail = addr_detail;
		this.tel = tel;
		this.job = job;
	}
	public MemberVO(String id, String password, String name, String addr, String addr_detail, String tel, String job,
			String enabled) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.addr = addr;
		this.addr_detail = addr_detail;
		this.tel = tel;
		this.job = job;
		this.enabled = enabled;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getenabled() {
		return enabled;
	}
	public void setenabled(String enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", password=" + password + ", name=" + name + ", addr=" + addr + ", addr_detail="
				+ addr_detail + ", tel=" + tel + ", job=" + job + ", enabled=" + enabled + "]";
	}

		
}