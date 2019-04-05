package com.nastech.upmureport.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.intellij.lang.annotations.JdkConstants.CursorType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity @Table(name="pfile") @Getter @NoArgsConstructor @Builder @AllArgsConstructor
public class Pfile implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private BigInteger fId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dId")
	private Pdir dId;
	
	private String name;
	
	private String contents;
	
	private LocalDateTime newDate;
	
	private LocalDateTime updateDate;
	
	private String localPath;
	
	private Boolean deleteFlag;
	
	public void deleteUpmuContent() {
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
}