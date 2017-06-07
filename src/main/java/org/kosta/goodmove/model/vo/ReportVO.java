package org.kosta.goodmove.model.vo;

public class ReportVO {
	private int report_no;
	private String category;
	private int reno;
	private String id;
	private String reporter;
	private String why;
	private String time_posted;
	private String process;
	public ReportVO() {
		super();
	}
	public ReportVO(int report_no, String category, int reno, String id, String reporter, String why,
			String time_posted, String process) {
		super();
		this.report_no = report_no;
		this.category = category;
		this.reno = reno;
		this.id = id;
		this.reporter = reporter;
		this.why = why;
		this.time_posted = time_posted;
		this.process = process;
	}
	public int getReport_no() {
		return report_no;
	}
	public void setReport_no(int report_no) {
		this.report_no = report_no;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getReno() {
		return reno;
	}
	public void setReno(int reno) {
		this.reno = reno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	public String getTime_posted() {
		return time_posted;
	}
	public void setTime_posted(String time_posted) {
		this.time_posted = time_posted;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	@Override
	public String toString() {
		return "ReportVO [report_no=" + report_no + ", category=" + category + ", reno=" + reno + ", id=" + id
				+ ", reporter=" + reporter + ", why=" + why + ", time_posted=" + time_posted + ", process=" + process
				+ "]";
	}
	
}
