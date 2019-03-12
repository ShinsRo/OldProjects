package com.nastech.upmureport.domain.entity;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeSystemPK implements Serializable{
	private static final long serialVersionUID = 3570716009500781077L;
	
	private Integer senior;
	private Integer junior;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		EmployeeSystemPK pk = (EmployeeSystemPK) o;
		return Objects.equals(senior, pk.senior) && Objects.equals(junior, pk.junior);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(senior, junior);
	}
}
