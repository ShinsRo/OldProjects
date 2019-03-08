package com.nastech.upmureport.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Data
@Table(name="posi")
public class Position {
	@Id
	@GeneratedValue
	private Integer posi_code;
	
	private Integer roll_level;
	private String posi_name;
	@Builder
	public Position(Integer roll_level,String posi_name) {
		this.roll_level=roll_level;
		this.posi_name=posi_name;
	}
	
}