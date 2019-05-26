package model;

public class AccountVO {
	private int income;
	private int spend;
	private String no;
	private String today;
	private String time;
	private String detail;
	private String type;
	public AccountVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountVO(int income, int spend, String detail, String type) {
		super();
		this.income = income;
		this.spend = spend;
		this.detail = detail;
		this.type = type;
	}

	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public int getSpend() {
		return spend;
	}
	public void setSpend(int spend) {
		this.spend = spend;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "AccountVO [income=" + income + ", spend=" + spend + ", no=" + no + ", today=" + today + ", time=" + time
				+ ", detail=" + detail + ", type=" + type + "]";
	}
	
}