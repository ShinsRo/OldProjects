package com.nastech.upmureport.domain.dto;

import com.nastech.upmureport.domain.entity.Dir;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class DirDto {

	private String dirId;
	private String dirName;
	private String userId;
	private String parentProjId;
	private String parentDirId;
	
	public Dir toEntity() {
		return Dir.builder()
				.dirId(Integer.valueOf(dirId))
				.dirName(dirName)
				.build();
	}
}
