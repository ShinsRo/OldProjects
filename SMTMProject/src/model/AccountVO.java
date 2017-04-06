package model;

public class AccountVO {
	private int income;
	private int spend;
	private String no;
	private String today;
	private String time;
	private String detail;

	public AccountVO() {
		super();
	}

	public AccountVO(int income, int spend, String no, String today, String time, String detail) {
		super();
		this.income = income;
		this.spend = spend;
		this.no = no;
		this.today = today;
		this.time = time;
		this.detail = detail;
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

	@Override
	public String toString() {
		return "AccountVO [income=" + income + ", spend=" + spend + ", no=" + no + ", today=" + today + ", time=" + time
				+ ", detail=" + detail + "]";
	}

}