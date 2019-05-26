package org.sejonghacker.botum;

public class TestVO {
	int id;
	String val;

	public TestVO() {
		super();
	}
	
	public TestVO(String val) {
		super();
		this.val = val;
	}

	
	public TestVO(int id, String val) {
		super();
		this.id = id;
		this.val = val;
	}

	public String getVal() {
		return val;
	}


	public void setVal(String val) {
		this.val = val;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "TestVO [id=" + id + ", val=" + val + "]";
	}
	
}
