package com.nastech.upmureport.feature.user.domain.dto;

import com.nastech.upmureport.feature.user.domain.entity.AuthInfo;
import com.nastech.upmureport.feature.user.domain.entity.Career;
import com.nastech.upmureport.feature.user.domain.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemAuthCareerDto {
	Member mem;
	AuthInfo auth;
	Career newCar;
}
