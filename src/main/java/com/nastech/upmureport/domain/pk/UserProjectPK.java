package com.nastech.upmureport.domain.pk;

import java.io.Serializable;
import java.util.Objects;

import com.nastech.upmureport.domain.entity.ProjStat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserProjectPK implements Serializable {
	private static final long serialVersionUID = -1975783332618600132L;
	
	private String user;
	private Integer project;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserProjectPK pk = (UserProjectPK) o;
		return Objects.equals(user, pk.user) && Objects.equals(project, pk.project);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(user, String.valueOf(project));
	}
}
