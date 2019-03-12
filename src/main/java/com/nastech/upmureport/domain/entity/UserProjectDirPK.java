package com.nastech.upmureport.domain.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class UserProjectDirPK implements Serializable {
	private static final long serialVersionUID = 110915459184422098L;
	
	private Integer user;
	private Long project;
	private Long dir;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserProjectDirPK pk = (UserProjectDirPK) o;
		return Objects.equals(user, pk.user) && Objects.equals(project, pk.project) && Objects.equals(dir, pk.dir);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(user, project, dir);
	}
}
