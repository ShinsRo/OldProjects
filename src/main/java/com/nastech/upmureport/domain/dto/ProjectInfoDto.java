package com.nastech.upmureport.domain.dto;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nastech.upmureport.controller.ProjectController;
import com.nastech.upmureport.domain.entity.MemberProject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfoDto {
	private String mpid;
	
	private ProjectDto pDto;
	
	private String mid;
	private String mname;
	private String pstat;
	private String prole;
	private String progressAvg;
	
	private BigInteger mcnt;
	private int queryOps;
	private Boolean dflag;
	
	
	// mp.*, p.st_date, p.ed_date
	public ProjectInfoDto(MemberProject el, int queryOps) {
		this.mpid = el.getMpid().toString();
		this.pDto = new ProjectDto(el);
		this.queryOps = queryOps;
	}
	
	// p.*, mp.progress_avg, mp.mcnt
	public ProjectInfoDto(Map<String, Object> el, int queryOps) {
		Log Logger = LogFactory.getLog(ProjectController.class);

		this.pDto = new ProjectDto();
		this.queryOps = queryOps;
		
		for(String key : el.keySet()) {
			Object value = el.get(key);
			if (value == null) continue;
			
			Matcher m = Pattern.compile("_(\\w)").matcher(key);
			
			StringBuilder sb = new StringBuilder();
			int last = 0;
			while(m.find()) {
				sb.append(key.substring(last, m.start()));
				sb.append(m.group(1).toUpperCase());
				last = m.end();
			}
			sb.append(key.substring(last));
			key = sb.toString();
			
			try {
				Field field = null;
				
				try { 								field = this.pDto.getClass().getDeclaredField(key); } 
				catch (NoSuchFieldException e) { 	field = this.getClass().getDeclaredField(key); }
				
				field.setAccessible(true);
				
				if (value instanceof java.sql.Timestamp) {
					value = ((java.sql.Timestamp) value).toLocalDateTime();
				} else if (value instanceof BigDecimal) {
					value = ((BigDecimal) value).toString();
				}
				
				if (field.getDeclaringClass().equals(ProjectInfoDto.class)) {
					field.set(this, field.getType().cast(value));
				} else {					
					field.set(this.pDto, field.getType().cast(value));
				}
				
			} catch (NoSuchFieldException e) {
				Logger.debug("DB 쿼리로 가져온 필드를 DTO에 매핑할 수 없습니다.");
			} catch (IllegalArgumentException e) {
				Logger.debug("매핑할 수 없는 타입의 필드입니다.");
			} catch (IllegalAccessException e) {
				Logger.debug("해당 필드에 접근 권한이 없습니다.");
			}
		}
		
	}
}
