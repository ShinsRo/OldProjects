package com.nastech.upmureport.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PfileLog {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private BigInteger LogId;
	
	private LocalDateTime newDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mId")
	private Member mId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fId")
	private Pfile fId;
	
	private String contents;	
	
	private LogStat stat;	

	public enum LogStat{
		CREATE("create"), UPDATE("update"), DELETE("delete");
		
		private String value;
		
		LogStat(String value){
			this.value = value;
		}
		
		public String getKey() {
            return name();
        }

        public String getValue() {
            return value;
        }
	}
}