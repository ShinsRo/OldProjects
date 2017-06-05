package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.PagingBean;

public interface MemberDAO {
	public MemberVO findMemberById(String id);

	public MemberVO login(MemberVO memberVO);

	void register(MemberVO vo);

	int idcheck(String id);

	int getTotalMember();

	void updateMember(MemberVO memberVO);

	int passwordCheck(String password);

	void deleteMember(String id, String password);

	public MemberVO forgotId(String name, String tel);

	public MemberVO forgotPass(String id,String name, String tel);
	
	public void changePass(String id,String password);

	int getTotalMemberCount();

	List<MemberVO> getMemberList_admin(PagingBean pagingBean);

	void deleteMember_admin(String id);

}
