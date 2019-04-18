package com.nastech.upmureport.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.domain.pk.MemberSystemPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MemberSystemPK.class)
public class MemberSystem {
	
	@Id
	@ManyToOne
	@JoinColumn(name="seniorId")
	private Member senior;
	
	@Id
	@ManyToOne
	@JoinColumn(name="juniorId")
	private Member junior;
}
