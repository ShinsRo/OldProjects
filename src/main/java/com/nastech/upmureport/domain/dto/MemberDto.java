package com.nastech.upmureport.domain.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class MemberDto {
	private Long mid;
	private String eid;
	private String name;
	private String birth;
	private String phoneNum;
	private LocalDate joinDate;
	private LocalDate retireDate;
	private Boolean dflag = false;
	private List<Career> career=new ArrayList<Career>();
	private String retireEid;
	
	
	public Member toEntity() {
		return Member.builder().
				mid(mid).
				eid(eid).
				name(name).
				birth(birth).
				phoneNum(phoneNum).
				joinDate(joinDate).
				retireDate(retireDate).dflag(dflag).
				career(career).retireEid(retireEid).build();
		}
}
