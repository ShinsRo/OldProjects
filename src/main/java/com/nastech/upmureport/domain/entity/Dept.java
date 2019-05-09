package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.nastech.upmureport.domain.entity.AuthInfo.AuthInfoBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Dept {
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger deptId;
	private String deptName;
}
