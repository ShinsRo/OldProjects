package com.nastech.upmureport.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Data
public class Dept {
	@Id
	@GeneratedValue
	private Integer dept_code;
	private String dept_name;
	@Builder
	public Dept(String dept_name) {
		this.dept_name=dept_name;
	}

}
