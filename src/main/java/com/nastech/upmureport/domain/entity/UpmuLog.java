package com.nastech.upmureport.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.nastech.upmureport.domain.pk.UpmuLogPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UpmuLogPK.class)
public class UpmuLog {
	
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private Integer LogId;
	
	private LocalDateTime newDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User userId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private UpmuContent upmuId;
	
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

