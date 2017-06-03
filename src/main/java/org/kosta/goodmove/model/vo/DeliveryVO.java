package org.kosta.goodmove.model.vo;

public class DeliveryVO {
	private String id;
	private String name;
	private String password;
	private String tel;
	private String is_delete;

	public DeliveryVO() {
		super();
	}

	public DeliveryVO(String id, String name, String password, String tel, String is_delete) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.tel = tel;
		this.is_delete = is_delete;
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

	public String getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}

	@Override
	public String toString() {
		return "DeliveryVO [id=" + id + ", name=" + name + ", password=" + password + ", tel=" + tel + ", is_delete="
				+ is_delete + "]";
	}

}
