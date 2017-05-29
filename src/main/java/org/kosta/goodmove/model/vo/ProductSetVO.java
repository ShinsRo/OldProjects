package org.kosta.goodmove.model.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProductSetVO {
	private List<String> ptitle;
	private List<String> kind;
	private List<String> pcontent;
	private List<MultipartFile> file;
	public ProductSetVO() {
		super();
	}
	public ProductSetVO(List<String> ptitle, List<String> kind, List<String> pcontent, List<MultipartFile> file) {
		super();
		this.ptitle = ptitle;
		this.kind = kind;
		this.pcontent = pcontent;
		this.file = file;
	}
	public List<String> getPtitle() {
		return ptitle;
	}
	public void setPtitle(List<String> ptitle) {
		this.ptitle = ptitle;
	}
	public List<String> getKind() {
		return kind;
	}
	public void setKind(List<String> kind) {
		this.kind = kind;
	}
	public List<String> getPcontent() {
		return pcontent;
	}
	public void setPcontent(List<String> pcontent) {
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
		return "ProductSetVO [ptitle=" + ptitle + ", kind=" + kind + ", pcontent=" + pcontent + ", file=" + file + "]";
	}

}
