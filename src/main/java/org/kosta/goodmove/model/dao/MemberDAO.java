package org.kosta.goodmove.model.dao;

import org.kosta.goodmove.model.vo.MemberVO;

public interface MemberDAO {

	MemberVO findByIdMember(String id);

}
