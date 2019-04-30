package com.nastech.upmureport.domain.entity.support;

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
