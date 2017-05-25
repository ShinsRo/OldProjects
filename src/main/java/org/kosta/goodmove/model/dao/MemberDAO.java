package org.kosta.goodmove.model.dao;

import org.kosta.goodmove.model.vo.MemberVO;

public interface MemberDAO {
	public MemberVO findMemberById(String id);
	
	public MemberVO login(MemberVO memberVO);
	
	void register(MemberVO vo);

	int idcheck(String id);

}
