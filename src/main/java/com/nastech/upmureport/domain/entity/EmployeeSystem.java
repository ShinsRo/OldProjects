package com.nastech.upmureport.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.domain.pk.EmployeeSystemPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(EmployeeSystemPK.class)
public class EmployeeSystem {
	
	@Id
	@ManyToOne
	@JoinColumn(name="seniorId")
	private User senior;
	
	@Id
	@ManyToOne
	@JoinColumn(name="juniorId")
	private User junior;
}
