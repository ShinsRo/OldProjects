package com.nastech.upmureport.domain.entity;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nastech.upmureport.domain.dto.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Member {
	
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private Long mid;
	@Column(unique=true)
	private String eid;
	private String name;
	private String birth;
	private String phoneNum;
	private LocalDate joinDate;
	private LocalDate retireDate;
	
	@Builder.Default
	private Boolean dflag = false;
	
	
	@OneToMany(mappedBy="member")
	@JsonManagedReference
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
				career(career).build();
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