package com.nastech.upmureport.domain.pk;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

public class MemberSystemPK implements Serializable{
	private static final long serialVersionUID = 3570716009500781077L;
	
	private Long senior;
	private Long junior;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MemberSystemPK pk = (MemberSystemPK) o;
		return Objects.equals(senior, pk.senior) && Objects.equals(junior, pk.junior);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(senior, junior);
	}
}
