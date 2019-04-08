package com.nastech.upmureport.domain.entity;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Member {
	
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger mid;
	private String eid;
	private String mName;
	private String birth;
	private String phoneNum;
	private LocalDate joinDate;
	private LocalDate retireDate;
	
	@Builder.Default
	private Boolean dFlag = false;
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="mid")
	private List<Career> career;
	
	/*
	public UserDto toDto() {
		return UserDto.builder().
				mid(mid).
				mname(mname).
				.build();
		}
	*/
}	