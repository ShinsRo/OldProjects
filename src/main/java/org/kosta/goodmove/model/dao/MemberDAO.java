package org.kosta.goodmove.model.dao;

import org.kosta.goodmove.model.vo.MemberVO;

public interface MemberDAO {
	public MemberVO findMemberById(String id);
	
	public MemberVO login(MemberVO memberVO);
	
	void register(MemberVO vo);

	int idcheck(String id);

	void updateMember(MemberVO memberVO);
	
	String passwordCheck(String password);
	
	void deleteMember(String id,String password);
	
	public String forgotId(String name,String tel);
	
	public String forgotPass(String id,String name,String tel);


}
