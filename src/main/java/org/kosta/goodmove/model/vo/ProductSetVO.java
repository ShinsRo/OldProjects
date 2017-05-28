package org.kosta.goodmove.model.vo;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProductSetVO {
	private String[] ptitle;
	private String[] kind;
	private String[] pcontent;
	private List<MultipartFile> file;
	public ProductSetVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductSetVO(String[] ptitle, String[] kind, String[] pcontent, List<MultipartFile> file) {
		super();
		this.ptitle = ptitle;
		this.kind = kind;
		this.pcontent = pcontent;
		this.file = file;
	}
	public String[] getPtitle() {
		return ptitle;
	}
	public void setPtitle(String[] ptitle) {
		this.ptitle = ptitle;
	}
	public String[] getKind() {
		return kind;
	}
	public void setKind(String[] kind) {
		this.kind = kind;
	}
	public String[] getPcontent() {
		return pcontent;
	}
	public void setPcontent(String[] pcontent) {
		this.pcontent = pcontent;
	}
	public List<MultipartFile> getFile() {
		return file;
	}
	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "ProductSetVO [ptitle=" + Arrays.toString(ptitle) + ", kind=" + Arrays.toString(kind) + ", pcontent="
				+ Arrays.toString(pcontent) + ", file=" + file + "]";
	}
	
}
