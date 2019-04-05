package com.nastech.upmureport.domain.dto;

import com.nastech.upmureport.domain.entity.support.Pstat;
import com.nastech.upmureport.domain.pk.UserProjectPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserProjectDto {
	private String userId;
	private Integer projId;
	private Pstat projStat;
	
	public UserProjectPK getUserProjectPK() {
		return new UserProjectPK(userId, projId);
	}
}
