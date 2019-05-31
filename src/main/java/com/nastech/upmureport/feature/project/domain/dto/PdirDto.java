package com.nastech.upmureport.feature.project.domain.dto;

import com.nastech.upmureport.feature.project.domain.entity.Pdir;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PdirDto {

	private String did;
	private String dname;
	private String parentDid;
	private String mid;
	private String mname;
	private String pid;

	public PdirDto(Pdir dir) {
		this.did = dir.getDid().toString();
		if (dir.getParentDir() == null) this.parentDid = "root";
		else this.parentDid = dir.getParentDir().getDid().toString();
		this.dname = dir.getDname();
		this.mid = dir.getMember().toString();
		this.mname = dir.getMember().getMid().toString();
		this.pid = dir.getProject().getPid().toString();
	}
}
