package com.nastech.upmureport.domain.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserProjectPK implements Serializable {
	private static final long serialVersionUID = -1975783332618600132L;
	
	private Long user;
	private Long project;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserProjectPK pk = (UserProjectPK) o;
		return Objects.equals(user, pk.project) && Objects.equals(project, pk.project);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(user, project);
	}
}
