package com.nastech.upmureport.domain.dto;

import com.nastech.upmureport.domain.entity.Career;
import com.nastech.upmureport.domain.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemCareerDto {
	Member mem;
	Career newCar;
}
