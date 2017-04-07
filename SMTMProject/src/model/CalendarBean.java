package model;

public class CalendarBean {
	private int month;
	private int lastDayOfMonth;
	private int firstDayOfMonth;
	public CalendarBean() {
		super();
	}
	public CalendarBean(int month, int lastDayOfMonth, int firstDayOfMonth) {
		super();
		this.month = month;
		this.lastDayOfMonth = lastDayOfMonth;
		this.firstDayOfMonth = firstDayOfMonth;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getLastDayOfMonth() {
		return lastDayOfMonth;
	}
	public void setLastDayOfMonth(int lastDayOfMonth) {
		this.lastDayOfMonth = lastDayOfMonth;
	}
	public int getFirstDayOfMonth() {
		return this.firstDayOfMonth;
	}
	public void setFirstDayOfMonth(int firstDayOfMonth) {
		this.firstDayOfMonth = firstDayOfMonth;
	}
	@Override
	public String toString() {
		return "CalendarBean [month=" + month + ", lastDayOfMonth=" + lastDayOfMonth + ", FirstDayOfMonth="
				+ firstDayOfMonth + "]";
	}
	
	
}
