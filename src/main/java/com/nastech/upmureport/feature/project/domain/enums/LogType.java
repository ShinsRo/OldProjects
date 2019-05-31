package com.nastech.upmureport.feature.project.domain.enums;

public enum LogType{
	PFILE("pfile"), ATTACHMENT("attachment"), PROJECT("project");
	
	private String value;
	
	LogType(String value){
		this.value = value;
	}
	
	public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }
}
