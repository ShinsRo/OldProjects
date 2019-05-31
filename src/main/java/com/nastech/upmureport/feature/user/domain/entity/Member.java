package com.nastech.upmureport.feature.user.domain.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nastech.upmureport.feature.user.domain.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Member {
	
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private Long mid;				//JPA 권고에 따른 고유 생성키값
	@Column(unique=true)		
	private String eid;				//사번 unique해야함
	private String name;			//이름
	private String birth;			//생일
	private String phoneNum;		//핸드폰번호	
	private LocalDate joinDate;		//입사한 날짜
	private LocalDate retireDate;	//퇴사한 날짜
	private String retireEid;		//퇴사 후 기존 사번을 복사 저장하기 위함 (기록용)
	
	@Builder.Default
	private Boolean dflag = false;	//퇴사 여부
	
	
	@OneToMany(mappedBy="member")
	@JsonManagedReference
	@Builder.Default
	private List<Career> career=new ArrayList<Career>();
	public MemberDto toDto() {
		return MemberDto.builder().
				mid(mid).
				eid(eid).
				name(name).
				birth(birth).
				phoneNum(phoneNum).
				joinDate(joinDate).
				retireDate(retireDate).dflag(dflag).
				career(career).retireEid(retireEid)
				.build();
		}
	
	
	/*
	public UserDto toDto() {
		return UserDto.builder().
				mid(mid).
				mname(mname).
				.build();
		}
	*/
}	