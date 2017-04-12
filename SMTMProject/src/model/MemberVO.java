package model;

public class MemberVO {
	private String id;
	private String name;
	private String password;
	private int total;
	private int limit;
	private int authority;

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public MemberVO() {
		super();
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public MemberVO(String id, String name, String password, int total, int limit) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.total = total;
		this.limit = limit;
	}

	public MemberVO(String id, String name, int authority) {
		super();
		this.id = id;
		this.name = name;
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", name=" + name + ", password=" + password + ", total=" + total + ", limit="
				+ limit + ", authority=" + authority + "]";
	}

}