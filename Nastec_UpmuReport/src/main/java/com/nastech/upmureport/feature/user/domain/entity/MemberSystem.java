package com.nastech.upmureport.feature.user.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.feature.user.domain.pk.MemberSystemPK;

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
	@JoinColumn(name="senior_id")
	private Member senior;
	
	@Id
	@ManyToOne
	@JoinColumn(name="junior_id")
	private Member junior;
}
