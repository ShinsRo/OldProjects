package org.kosta.goodmove.model.vo;

public class DeliveryVO {
	private String id;
	private String name;
	private String password;
	private String tel;
	private String is_deleted;

	public DeliveryVO() {
		super();
	}

	public DeliveryVO(String id, String name, String password, String tel, String is_deleted) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.tel = tel;
		this.is_deleted = is_deleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public String toString() {
		return "DeliveryVO [id=" + id + ", name=" + name + ", password=" + password + ", tel=" + tel + ", is_deleted="
				+ is_deleted + "]";
	}

}
