package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.MemberVO;

public interface MemberService {
	
	public MemberVO login(MemberVO memberVO);
	
	void register(MemberVO vo);

	int idcheck(String id);

	public MemberVO findMemberById(String id);
	
}
