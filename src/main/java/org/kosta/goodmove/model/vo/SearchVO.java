package org.kosta.goodmove.model.vo;

public class SearchVO {
private String Mcategory;
private String Scategory;
private String word;
public SearchVO() {
	super();
	// TODO Auto-generated constructor stub
}
public SearchVO(String mcategory, String scategory, String word) {
	super();
	Mcategory = mcategory;
	Scategory = scategory;
	this.word = word;
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
public String getWord() {
	return word;
}
public void setWord(String word) {
	this.word = word;
}
@Override
public String toString() {
	return "SearchVO [Mcategory=" + Mcategory + ", Scategory=" + Scategory + ", word=" + word + "]";
}

}
