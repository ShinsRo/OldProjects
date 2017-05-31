package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.MemberVO;

public interface MemberService {

	public MemberVO login(MemberVO memberVO);

	void register(MemberVO vo);

	int idcheck(String id);

	public MemberVO findMemberById(String id);

	public int getMemberCount();

	void updateMember(MemberVO memberVO);

	public String forgotId(String name, String tel);

	public String forgotPass(String id, String name, String tel);
	
	int passwordCheck(String password);
	
	void deleteMember(String id,String password);
}
