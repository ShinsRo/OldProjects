package com.nastech.upmureport.feature.user.domain.dto;

import java.util.List;

import com.nastech.upmureport.feature.user.domain.entity.Dept;
import com.nastech.upmureport.feature.user.domain.entity.Posi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptPosiDto {
	List<Dept> deptList;
	List<Posi> posiList;
}
