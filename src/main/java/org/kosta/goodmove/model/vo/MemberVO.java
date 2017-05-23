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
	private String addr_code;
	private String tel;
	private String job;
	private String tno;
	private String ano;
	public MemberVO() {
		super();
	}
	public MemberVO(String id, String password, String name, String addr_code, String tel, String job, String tno,
			String ano) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.addr_code = addr_code;
		this.tel = tel;
		this.job = job;
		this.tno = tno;
		this.ano = ano;
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
	public String getAddr_code() {
		return addr_code;
	}
	public void setAddr_code(String addr_code) {
		this.addr_code = addr_code;
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
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", password=" + password + ", name=" + name + ", addr_code=" + addr_code
				+ ", tel=" + tel + ", job=" + job + ", tno=" + tno + ", ano=" + ano + "]";
	}
	
}
