package com.nastech.upmureport.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.nastech.upmureport.domain.entity.Position.PositionBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Builder
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private Integer user_id;
	private String user_name;
	private String user_pass;
	@ManyToOne
	@JoinColumn(name="dept_code")
	private Dept dept;
}