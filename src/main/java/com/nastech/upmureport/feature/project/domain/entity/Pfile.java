package com.nastech.upmureport.feature.project.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity @Table(name="pfile") @Getter @NoArgsConstructor @Builder @AllArgsConstructor
public class Pfile implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private BigInteger fId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="did")
	private Pdir pdir;
	
	@NotNull
	private String name;
	
	@NotNull @Lob
	private String contents;
	
	@CreationTimestamp
	private LocalDateTime newDate;
	
	@UpdateTimestamp
	private LocalDateTime updateDate;
	
	@Builder.Default
	private Boolean deleteFlag=false;
	
	
	public void deletePfile() {
		this.deleteFlag = true;
	}
	
	public void changeName(String newName) {
		this.name = newName;
	}
	
	public void changeContents(String newContents) {
		this.contents = newContents;
	}
	
	public void updateDate() {
		this.updateDate = LocalDateTime.now();
	}
	
	public void movePdir(Pdir pdir) {
		this.pdir = pdir;
	}
}