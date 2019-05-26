package org.kosta.goodmove.model.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BoardUpdateParamDTO {
	List<MultipartFile> corFile;
	String deleteStack;
	List<String> pno;
	String maxProductSize;
	public BoardUpdateParamDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardUpdateParamDTO(List<MultipartFile> corFile, String deleteStack, List<String> pno,
			String maxProductSize) {
		super();
		this.corFile = corFile;
		this.deleteStack = deleteStack;
		this.pno = pno;
		this.maxProductSize = maxProductSize;
	}
	public List<MultipartFile> getCorFile() {
		return corFile;
	}
	public void setCorFile(List<MultipartFile> corFile) {
		this.corFile = corFile;
	}
	public String getDeleteStack() {
		return deleteStack;
	}
	public void setDeleteStack(String deleteStack) {
		this.deleteStack = deleteStack;
	}
	public List<String> getPno() {
		return pno;
	}
	public void setPno(List<String> pno) {
		this.pno = pno;
	}
	public String getMaxProductSize() {
		return maxProductSize;
	}
	public void setMaxProductSize(String maxProductSize) {
		this.maxProductSize = maxProductSize;
	}
	@Override
	public String toString() {
		return "BoardUpdateParamDTO [corFile=" + corFile + ", deleteStack=" + deleteStack + ", pno=" + pno
				+ ", maxProductSize=" + maxProductSize + "]";
	}
	
	
}
