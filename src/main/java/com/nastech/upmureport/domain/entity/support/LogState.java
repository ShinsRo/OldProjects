package com.nastech.upmureport.domain.entity.support;

public enum LogState{
	CREATE("create"), UPDATE("update"), DELETE("delete"), MOVE("move"), COPY("copy");
	
	private String value;
	
	LogState(String value){
		this.value = value;
	}
	
	public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }
}
