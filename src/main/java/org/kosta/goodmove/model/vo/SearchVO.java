package org.kosta.goodmove.model.vo;

public class SearchVO {
private String Mcategory;
private String Scategory;
private String keyword;
public SearchVO() {
	super();
	// TODO Auto-generated constructor stub
}
public SearchVO(String mcategory, String scategory, String keyword) {
	super();
	this.Mcategory = mcategory;
	this.Scategory = scategory;
	this.keyword = keyword;
}
public String getMcategory() {
	return Mcategory;
}
public void setMcategory(String mcategory) {
	Mcategory = mcategory;
}
public String getScategory() {
	return Scategory;
}
public void setScategory(String scategory) {
	Scategory = scategory;
}
public String getKeyword() {
	return keyword;
}
public void setKeyword(String keyword) {
	this.keyword = keyword;
}
@Override
public String toString() {
	return "SearchVO [Mcategory=" + Mcategory + ", Scategory=" + Scategory + ", keyword=" + keyword + "]";
}




}